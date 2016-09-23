package com.claraboia.bibleandroid.tests

import com.claraboia.bibleandroid.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.getBibleDir
import java.io.*
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection

/**
 * Created by lucasbatagliao on 23/09/16.
 */


fun downloadBibleFile(){
    val name = "acf"
    val file = File("${getBibleDir()}/$name${BIB_FILE_EXTENSION}")

    var connection: HttpsURLConnection? = null
    if (!file.exists()) {
        try {
            val url = URL("https://raw.githubusercontent.com/batagliao/bibles/master/xml/acf.bib")
            //connection = url.openConnection() as HttpsURLConnection

            val input = DataInputStream(url.openStream())

            val buffer = ByteArray(1024)
            var length: Int

            //val bis = BufferedInputStream(connection.inputStream)
            //val ba = ByteArray(50)

            //val osw = OutputStreamWriter(file.outputStream(), Charset.forName("UTF-8").newEncoder())
            //val bos = BufferedOutputStream(file.outputStream())

            val outuput = FileOutputStream(file.absolutePath)

            //val str = StringBuilder()

//            while (bis.read(ba) > -1) {
//                str.append( String(ba,  Charset.forName("UTF-8")))
//            }
//
//            osw.write(str.toString())
//
//            bis.close()
//            osw.close()
            //bos.close()

            do {
                length = input.read(buffer)
                outuput.write(buffer, 0, length)
            }while (length > 0)

            input.close()
            outuput.close()

        } finally {
            connection?.disconnect()
        }
    }
}