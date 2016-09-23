package com.claraboia.bibleandroid.tests.parsers

import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import com.claraboia.bibleandroid.tests.downloadBibleFile
import com.claraboia.bibleandroid.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.getBibleDir
import com.claraboia.bibleandroid.getBibleStream
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
        downloadBibleFile()
    }


    @Test
    fun testParse() {

        val stream = getBibleStream("acf.bib")
        var bible : Bible? = null
        stream.use {
            val parser = BibleSaxParser()
            bible = parser.parse(it)
        }

        Assert.assertEquals(66, bible?.books?.size)
    }
}