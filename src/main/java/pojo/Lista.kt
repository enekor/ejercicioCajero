package pojo

import kotlin.random.Random

object Lista {

    var usuarios:List<Usuario> = getList()

    private fun getList():List<Usuario>{
        val usr1 = Usuario("Eren","eren@banco","1234", Random.nextDouble(0.0,4000.0))
        val usr2 = Usuario("Maria","maria@banco","1234", Random.nextDouble(0.0,4000.0))
        val usr3 = Usuario("Rose","rose@banco","1234", Random.nextDouble(0.0,4000.0))
        val usr4 = Usuario("Sina","sina@banco","1234", Random.nextDouble(0.0,4000.0))
        return listOf(usr1,usr2,usr3,usr4)
    }
}

