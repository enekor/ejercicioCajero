package logger

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object Logger{

   private val uri = """${System.getProperty("user.dir")}${File.separator}src${File.separator}main"""+
                        """${File.separator}resources${File.separator}logger.log"""

    /**
     * escribe en el logger la informacion
     * @param codigo que tipo de informacion se va a guardar
     * @param message mensaje que le acompaña al codigo
     */
    fun log (codigo:String, message:String){
        val bw = BufferedWriter(FileWriter(uri,true))
        bw.append("\n$codigo -> $message")
        bw.flush()
        bw.close()
    }

    fun test(){
        print(uri)
    }
}

fun main() {
    Logger.log(Log.ERROR, "test")
}