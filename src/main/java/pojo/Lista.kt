package pojo

import encriptation.SHA512
import kotlin.random.Random

object Lista {

    var usuarios:List<Usuario> = getList()

    private fun getList():List<Usuario>{
        val usr1 = Usuario("Eren","eren@banco", SHA512.getSHA512("1234"), Random.nextDouble(0.0,4000.0))
        val usr2 = Usuario("Maria","maria@banco",SHA512.getSHA512("1234"), Random.nextDouble(0.0,4000.0))
        val usr3 = Usuario("Rose","rose@banco",SHA512.getSHA512("1234"), Random.nextDouble(0.0,4000.0))
        val usr4 = Usuario("Sina","sina@banco",SHA512.getSHA512("1234"), Random.nextDouble(0.0,4000.0))
        return listOf(usr1,usr2,usr3,usr4)
    }
}

