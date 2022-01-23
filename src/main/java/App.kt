import encriptation.Bcrypt
import encriptation.RSA
import java.io.File

fun main() {

    val rsa = RSA.getInstance()

    print(Bcrypt.checkPasswd("1234","""$2a$12$9LckbPJ2sa0un376/2WbzezALtdG6gcP3jeUh6tShKDeuQjebrjOu"""))

}