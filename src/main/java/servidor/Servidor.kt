package servidor

import encriptation.RSA
import java.io.File
import java.net.ServerSocket
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Path

class Servidor {

    private lateinit var servidor:ServerSocket
    private lateinit var cliente:Socket
    private val puerto = 5000

    /**
     * inicio del servicio
     */
    init {
        clavesRSA()
        servidor = ServerSocket(puerto)

        do {
            println("Esperando usuarios")
            cliente = servidor.accept()

            //val id = DataInputStream(cliente.getInputStream()).readAllBytes().toString()

            GestionCliente(cliente).start()
        } while (true)
    }
}

private fun clavesRSA(){
    val clavesPath = System.getProperty("user.dir")+"${File.separator}src${File.separator}main${File.separator}resources${File.separator}claves"
    val dir = File(clavesPath)
    val rsa = RSA.getInstance()

    if(!dir.exists()){
        Files.createDirectories(Path.of(clavesPath))
    }

    rsa.crearClavesRSA(clavesPath+File.separator)
}

fun main() {
    Servidor()
}