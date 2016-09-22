package com.claraboia.bibleandroid.tests.parsers

import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import com.claraboia.bibleandroid.utils.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.utils.getBibleDir
import com.claraboia.bibleandroid.utils.getBibleStream
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.net.URL
import javax.net.ssl.HttpsURLConnection

@RunWith(AndroidJUnit4::class)
@SmallTest
class BibleSaxParserTest  {

    @Before
    fun start() {
        val name = "acf"
        val file = File("${getBibleDir()}/$name$BIB_FILE_EXTENSION")

        var connection: HttpsURLConnection? = null
        if (!file.exists()) {
            try {
                val url = URL("https://raw.githubusercontent.com/batagliao/bibles/master/xml/acf.bib")
                connection = url.openConnection() as HttpsURLConnection
                val bis = BufferedInputStream(connection.inputStream)
                val ba = ByteArray(50)

                val bos = BufferedOutputStream(file.outputStream())

                while (bis.read(ba) > -1) {
                    bos.write(ba)
                }

                bis.close()
                bos.close()
            } finally {
                connection?.disconnect()
            }
        }
    }


    @Test
    fun testParse() {

        val stream = getBibleStream("acf.bib")
        var bible : Bible? = null
        stream.use {
            val parser = BibleSaxParser()
            bible = parser.parse(it)
        }

        Assert.assertEquals(66, bible?.Books?.size)
    }
}