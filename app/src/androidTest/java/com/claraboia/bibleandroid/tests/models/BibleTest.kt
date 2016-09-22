package com.claraboia.bibleandroid.tests.models

import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.utils.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.utils.getBibleDir
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Created by lucas.batagliao on 22/09/2016.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class BibleTest {

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
    fun loadtest(){
        val bible = Bible.load("acf")

        Assert.assertNotNull(bible)
        Assert.assertEquals(66, bible.Books.size)
    }
}