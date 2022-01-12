package cliente

import java.net.ServerSocket
import java.net.Socket

class Cliente {

    private lateinit var server:Socket
    private val puerto = 5000

    init {
        server = Socket("localhost",puerto)

        while(true){
            println("Que operacion desea hacer \n1: Sacar dinero " +
                    "\n2: Meter dinero " +
                    "\n3: Consultar saldo " +
                    "\n4: Salir")
            val operacion:Int = readln().toInt()

            when(operacion){
                1 -> {
                    println("ingrese cantidad a sacar")

                }
            }
        }

    }
}