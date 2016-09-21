package com.claraboia.bibleandroid.tests.parsers

import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class BibleSaxParserTest  {

    @Test
    fun testParse() {

        val context = InstrumentationRegistry.getTargetContext()
        val assets = context.assets
        val stream = assets.open("acf.bib")
        var bible : Bible? = null
        stream.use {
            val parser = BibleSaxParser()
            bible = parser.parse(it)
        }

        Assert.assertEquals(66, bible?.Books?.size)
    }
}