package com.claraboia.bibleandroid.tests.models

import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.claraboia.bibleandroid.helpers.loadBible
import com.claraboia.bibleandroid.models.BibleTranslation
import com.claraboia.bibleandroid.tests.downloadBibleFile
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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

}