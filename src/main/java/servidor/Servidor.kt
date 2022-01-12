package servidor

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

            GestionCliente(cliente)
        } while (true)
    }
}