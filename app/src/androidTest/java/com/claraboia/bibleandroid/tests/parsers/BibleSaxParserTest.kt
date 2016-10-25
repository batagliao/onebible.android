package com.claraboia.bibleandroid.tests.parsers

import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.helpers.getBibleStream
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import com.claraboia.bibleandroid.tests.downloadBibleFile
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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