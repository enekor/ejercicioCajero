package encriptation

import org.mindrot.jbcrypt.BCrypt

object Bcrypt {

    fun encriptPasswd(passwd:String):String{
        val salt = BCrypt.gensalt(12)
        val hashedPasswd = BCrypt.hashpw(passwd,salt)

        return hashedPasswd
    }

    fun checkPasswd(passwd:String,stored_hash:String?):Boolean{

        if(stored_hash==null || !stored_hash.startsWith("""$2a$"""))
            throw IllegalArgumentException("invalid hash provided for comparison")

        return BCrypt.checkpw(passwd,stored_hash)
    }
}