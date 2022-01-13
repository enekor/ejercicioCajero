package servidor

import logger.Log
import logger.Logger
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class GestionCliente(socket:Socket):Thread() {

    val cliente = socket
    //var id =
    var salir = false

    override fun run() {
        println("he entradi dentro del hilo")
        if(checkUser()){
            logger(0,"")
            while(!salir) {
                try {
                    val operacion = DataInputStream(cliente.getInputStream()).readInt()
                    when (operacion) {
                        1 -> {
                            val dinero = DataInputStream(cliente.getInputStream()).readDouble()
                            if (tieneSaldo(dinero)) {
                                restarDinero(dinero)
                                DataOutputStream(cliente.getOutputStream()).writeUTF("se ha sacado $dinero de su saldo")
                            }
                        }
                        2 -> {
                            println("estoy dentro")
                            val dinero = DataInputStream(cliente.getInputStream()).readDouble()
                            sumarDinero(dinero)
                            DataOutputStream(cliente.getOutputStream()).writeUTF("dinero sumado con exito a la cuenta")
                        }
                        3 -> {
                            DataOutputStream(cliente.getOutputStream()).writeUTF("Su saldo actual es de ${leerSaldo()}")
                        }
                        4 -> {
                            salir = true
                            cliente.close()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        else{
            logger(5,"usuario/passwd incorrecto")
            cliente.close()
        }
    }

    private fun checkUser():Boolean{
       // TODO("mira si existe o no ese usuario y si la passwd coincide")
        return true
    }
    private fun tieneSaldo(dinero:Double):Boolean{
        TODO("mirar en la base de datos si tiene saldo suficiente o no")
    }

    private fun restarDinero(dinero:Double){
        TODO("resta el dinero que acaba de sacar")
    }

    private fun sumarDinero(dinero:Double){
        TODO("suma el dinero a la cuenta")
    }

    private fun leerSaldo():Double{
        TODO("devuelve el saldo disponible para esta cuenta")
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