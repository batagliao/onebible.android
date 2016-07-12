package com.claraboia.bibleandroid.tests.parsers

import android.test.AndroidTestCase
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import junit.framework.Assert

/**
 * Created by lucas.batagliao on 12/07/2016.
 */
class BibleSaxParserTest : AndroidTestCase() {

    fun testParse() {

        val assets = this.context.assets
        val stream = assets.open("acf.bib")
        var bible : Bible? = null
        stream.use {
            val parser = BibleSaxParser()
            bible = parser.parse(it)
        }

        Assert.assertEquals(66, bible?.Books?.size)
    }
}