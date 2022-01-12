package cliente

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class Cliente {

    private lateinit var server:Socket
    private val puerto = 5000
    private var salir = false

    init {
        server = Socket("localhost",puerto)

        while(!salir){
            println("Que operacion desea hacer \n1: Sacar dinero " +
                    "\n2: Meter dinero " +
                    "\n3: Consultar saldo " +
                    "\n4: Salir")
            val operacion:Int = readln().toInt()

            when(operacion){
                1 -> {
                    DataOutputStream(server.getOutputStream()).write(1)
                    println("Ingrese cantidad a sacar")

                    val dinero = readln()
                    try {
                        DataOutputStream(server.getOutputStream()).writeDouble(dinero.toDouble())
                        println(DataInputStream(server.getInputStream()).readAllBytes().toString())
                    }catch (e:Exception){
                        println("No ha introducido un valor valido, se acepta x o x.x")
                    }
                }
                2 -> {
                    DataOutputStream(server.getOutputStream()).write(2)
                    println("Ingrese cuanto dinero va a querer sacar")
                    val dinero = readln()
                    try {
                        DataOutputStream(server.getOutputStream()).writeDouble(dinero.toDouble())
                        println(DataInputStream(server.getInputStream()).readAllBytes().toString())
                    }catch (e:Exception){
                        println("No ha introducido un valor valido, se acepta x o x.x")
                    }
                }
                3 -> {
                    DataOutputStream(server.getOutputStream()).write(3)
                    println(DataInputStream(server.getInputStream()).readAllBytes().toString())
                }
                4 -> {
                    DataOutputStream(server.getOutputStream()).write(4)
                    salir = true
                    println("Tenga un buen dia")
                }
            }
        }
    }
}