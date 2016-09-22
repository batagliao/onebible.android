package com.claraboia.bibleandroid.tests.models

import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.models.Bible
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by lucas.batagliao on 22/09/2016.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class BibleTest {

    @Test
    fun loadtest(){
        val bible = Bible.load("acf")

        Assert.assertNotNull(bible)
        Assert.assertEquals(66, bible.Books.size)
    }
}