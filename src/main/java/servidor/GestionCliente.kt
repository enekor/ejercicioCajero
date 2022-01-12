package servidor

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class GestionCliente(socket:Socket, clienteId:String):Thread() {

    val cliente = socket
    val id = clienteId
    var salir = false

    override fun run() {
        while(!salir) {
            try {
                val operacion = DataInputStream(cliente.getInputStream()).read()
                when (operacion) {
                    1 -> {
                        val dinero = DataInputStream(cliente.getInputStream()).readDouble()
                        if (tieneSaldo(dinero)) {
                            restarDinero(dinero)
                            DataOutputStream(cliente.getOutputStream()).writeChars("se ha sacado $dinero de su saldo")
                        }
                    }
                    2 -> {
                        val dinero = DataInputStream(cliente.getInputStream()).readDouble()
                        sumarDinero(dinero)
                        DataOutputStream(cliente.getOutputStream()).writeChars("dinero sumado con exito a la cuenta")
                    }
                    3 -> {
                        DataOutputStream(cliente.getOutputStream()).writeChars("Su saldo actual es de ${leerSaldo()}")
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
}