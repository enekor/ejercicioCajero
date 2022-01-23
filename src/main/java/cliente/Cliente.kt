package cliente

import encriptation.SHA512
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import kotlin.system.exitProcess

class Cliente {

    private lateinit var server:Socket
    private val puerto = 5000
    private var salir = false

    /**
     * inicio del servicio
     */
    init {
        server = Socket("localhost",puerto)

        println("Inserte su usuario")
        DataOutputStream(server.getOutputStream()).writeUTF(readln())

        println("Inserte la contraseña")
        DataOutputStream(server.getOutputStream()).writeUTF(SHA512.getSHA512(readln()))

        println(DataInputStream(server.getInputStream()).readUTF())
        val acceso = DataInputStream(server.getInputStream()).readInt()
        println(acceso)

        if(acceso == 1){
            while(!salir){
                println("Que operacion desea hacer \n1: Sacar dinero " +
                        "\n2: Meter dinero " +
                        "\n3: Consultar saldo " +
                        "\n4: Salir")
                val operacion:Int = readln().toInt()
                println("$operacion")

                when(operacion){
                    1 -> {
                        extraccion()
                    }
                    2 -> {
                        deposito()
                    }
                    3 -> {
                       consulta()
                    }
                    4 -> {
                        cerrarSesion()
                    }
                }
            }
        }else{
            println("adios")
            exitProcess(0)
        }

    }

    /**
     * cierra la sesion actual
     */
    private fun cerrarSesion() {
        DataOutputStream(server.getOutputStream()).writeInt(4)
        salir = true
        println("Tenga un buen dia")
    }

    /**
     * pide al servidor el saldo actual de la cuenta y lo pasa por pantalla
     */
    private fun consulta() {
        DataOutputStream(server.getOutputStream()).writeInt(3)
        println(DataInputStream(server.getInputStream()).readUTF())
    }

    /**
     * pide al servidor que haga un deposito de una cantidad x de dinero
     */
    private fun deposito() {
        DataOutputStream(server.getOutputStream()).writeInt(2)
        println("Ingrese cuanto dinero va a querer meter")
        val dinero = readln()
        try {
            DataOutputStream(server.getOutputStream()).writeDouble(dinero.toDouble())
            println(DataInputStream(server.getInputStream()).readUTF())
        }catch (e:Exception){
            println("No ha introducido un valor valido, se acepta x o x.x")
        }
    }

    /**
     * pide al servidor que saque una cantidad x de dinero de la cuenta
     */
    private fun extraccion() {
        DataOutputStream(server.getOutputStream()).writeInt(1)
        println("Ingrese cantidad a sacar")

        val dinero = readln()
        try {
            DataOutputStream(server.getOutputStream()).writeDouble(dinero.toDouble())
            println(DataInputStream(server.getInputStream()).readUTF())
        }catch (e:Exception){
            println("No ha introducido un valor valido, se acepta x o x.x")
        }
    }
}

fun main() {
    Cliente()
}