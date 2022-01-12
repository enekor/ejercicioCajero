package servidor

import java.net.Socket

class GestionCliente(socket:Socket):Thread() {

    val cliente = socket

}