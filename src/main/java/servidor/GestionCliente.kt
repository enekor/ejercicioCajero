package servidor

import encriptation.Bcrypt
import encriptation.RSA
import logger.Log
import logger.Logger
import pojo.Lista
import pojo.Usuario
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.net.Socket

class GestionCliente(socket:Socket):Thread() {

    val cliente = socket
    var id:String? = null
    var salir = false

    override fun run() {
        if(checkUser()){
            DataOutputStream(cliente.getOutputStream()).writeUTF("Usuario confirmado")
            DataOutputStream(cliente.getOutputStream()).writeInt(1)
            logger(0,"")
            while(!salir) {
                try {
                    println("esperando operacion")
                    val operacion = DataInputStream(cliente.getInputStream()).readInt()
                    when (operacion) {
                        1 -> {
                            sacarDinero()
                        }
                        2 -> {
                            meterDinero()
                        }
                        3 -> {
                            consultarSaldo()
                        }
                        4 -> {
                            cerrarSesion()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        else{
            logger(5,"usuario/passwd incorrecto")
            DataOutputStream(cliente.getOutputStream()).writeUTF("Usuario o contraseña no validos")
            DataOutputStream(cliente.getOutputStream()).writeInt(0)
            cliente.close()
        }
    }

    /**
     * se asegura que el usuario existe en el sistema
     * @return true si existe, false si no
     */
    private fun checkUser():Boolean{
        val privateKey = System.getProperty("user.dir")+"${File.separator}src${File.separator}main${File.separator}resources${File.separator}claves${File.separator}-rsa-private.dat"
        var usuarios = Lista.usuarios
        val ussr:String = DataInputStream(cliente.getInputStream()).readUTF()
        var pass:String = DataInputStream(cliente.getInputStream()).readUTF()
        val rsa = RSA.getInstance()

        pass = rsa.descifrarRSA(pass,privateKey)
        usuarios = usuarios.filter { v->v.usuario==ussr && Bcrypt.checkPasswd(pass,v.passwd) }
//        println("usuarios ${usuarios.size}")

        if(usuarios.isNotEmpty()){
            id = usuarios[0].id
        }

        return usuarios.isNotEmpty()
    }

    /**
     * se asegura que tiene saldo suficiente antes de sacar el dinero
     * @return true si tiene, false si no
     */
    private fun tieneSaldo(dinero:Double):Boolean=
        Lista.usuarios.filter { v-> v.id == id }[0].dinero>dinero


    /**
     * resta el dinero de la cuenta
     * @param dinero la cantidad a restar
     */
    private fun restarDinero(dinero:Double){
        Lista.usuarios.filter { v-> v.id == id }[0].dinero -= dinero
        logger(1,"$dinero")
    }

    /**
     * suma el dinero depositado en la cuenta
     * @param dinero la cantidad a depositar en la cuenta
     */
    private fun sumarDinero(dinero:Double){
        Lista.usuarios.filter { v-> v.id == id }[0].dinero+=dinero
        logger(2,"$dinero")
    }

    /**
     * lee el dinero que hay en la cuenta
     * @return los findos disponibles en la cuenta
     */
    private fun leerSaldo():Double =
        Lista.usuarios.filter { v-> v.id == id }[0].dinero

    /**
     * se ocupa de mirar que haya el dinero suficiente antes de sacar el dinero, lo saca si lo hay, avisa al usuario y lo apunta en el logger
     */
    private fun sacarDinero(){
        val dinero = DataInputStream(cliente.getInputStream()).readDouble()
        if (tieneSaldo(dinero)) {
            restarDinero(dinero)
            DataOutputStream(cliente.getOutputStream()).writeUTF("se ha sacado $dinero de su saldo")
        }else{
            logger(6,"Saldo insuficiente")
            DataOutputStream(cliente.getOutputStream()).writeUTF("No tiene el saldo necesario")
        }
    }

    /**
     * se ocupa de meter el dinero en la cuenta, avisar al usuario de que el deposito ha sido un exito y lo apunta en el logger
     */
    private fun meterDinero(){
        val dinero = DataInputStream(cliente.getInputStream()).readDouble()
        sumarDinero(dinero)
        DataOutputStream(cliente.getOutputStream()).writeUTF("dinero sumado con exito a la cuenta")
    }

    /**
     * cierra la sesion del usuario y lo apunta en el logger
     * TODO(guardar la lista modificada en el json de almacen)
     */
    private fun cerrarSesion(){
        salir = true
        logger(4,"")
        println("usuario $id ha cerrado sesion")
        cliente.close()
    }

    /**
     * consulta el saldo, se lo pasa al cliente y lo apunta en el logger
     */
    private fun consultarSaldo(){
        DataOutputStream(cliente.getOutputStream()).writeUTF("Su saldo actual es de ${leerSaldo()}")
        logger(3,"")
    }

    /**
     * se ocupa de enviar al log la informacion
     */
    private fun logger(operacion:Int,informacion:String){
        when(operacion){
            0 -> Logger.log(Log.INICIO_SESION,"sesion iniciada para usuario $id")
            1 -> Logger.log(Log.SACAR_DINERO,"dinero retirado -> $informacion")
            2 -> Logger.log(Log.METER_DINERO,"dinero depositado -> $informacion")
            3 -> Logger.log(Log.CONSULTA,"consulta de dinero")
            4 -> Logger.log(Log.FIN_SESION,"fin de sesion del usuario $id")
            5 -> Logger.log(Log.ERROR,"Excepcion: $informacion")
        }
    }
}