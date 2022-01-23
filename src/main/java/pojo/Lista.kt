package pojo

import encriptation.Bcrypt
import encriptation.SHA512
import kotlin.random.Random

object Lista {

    var usuarios:List<Usuario> = getList()

    private fun getList():List<Usuario>{
        val usr1 = Usuario("Eren","eren@banco", encriptPasswd("1234"), Random.nextDouble(0.0,4000.0))
        val usr2 = Usuario("Maria","maria@banco", encriptPasswd("1234"), Random.nextDouble(0.0,4000.0))
        val usr3 = Usuario("Rose","rose@banco", encriptPasswd("1234"), Random.nextDouble(0.0,4000.0))
        val usr4 = Usuario("Sina","sina@banco", encriptPasswd("1234"), Random.nextDouble(0.0,4000.0))
        return listOf(usr1,usr2,usr3,usr4)
    }

    private fun encriptPasswd(passwd:String):String{
        var pass = SHA512.getSHA512(passwd)
        pass = Bcrypt.encriptPasswd(pass)
        return pass
    }
}

