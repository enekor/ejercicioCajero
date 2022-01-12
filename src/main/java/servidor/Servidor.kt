package servidor

import java.io.DataInputStream
import java.net.ServerSocket
import java.net.Socket

class Servidor {

    private lateinit var servidor:ServerSocket
    private lateinit var cliente:Socket
    private val puerto = 5000

    init {
        servidor = ServerSocket(puerto)

        do {
            println("Esperando usuarios")
            cliente = servidor.accept()

            val id = DataInputStream(cliente.getInputStream()).readAllBytes().toString()

            GestionCliente(cliente,id).start()
        } while (true)
    }
}