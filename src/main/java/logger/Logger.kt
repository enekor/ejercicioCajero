package logger

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object Logger{

   private val uri = """${System.getProperty("user.dir")}${File.separator}src${File.separator}main"""+
                        """${File.separator}resources${File.separator}logger.log"""
    fun log (codigo:String, message:String){
        val bw = BufferedWriter(FileWriter(uri))
        bw.write("$codigo -> $message")
        bw.flush()
        bw.close()
    }

    public fun test(){
        print(uri)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        log(Log.ERROR,"quiero irme a casa")
    }
}