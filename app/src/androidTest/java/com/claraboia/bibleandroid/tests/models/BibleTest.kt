package com.claraboia.bibleandroid.tests.models

import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.tests.downloadBibleFile
import com.claraboia.bibleandroid.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.getBibleDir
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.OutputStreamWriter
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection

/**
 * Created by lucas.batagliao on 22/09/2016.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class BibleTest {

    @Before
    fun start() {
        downloadBibleFile()
    }

    @Test
    fun loadtest(){
        val bible = Bible.load("acf")

        Assert.assertNotNull(bible)
        Assert.assertEquals(66, bible.books.size)
    }
}