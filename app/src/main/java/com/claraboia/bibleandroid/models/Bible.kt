package com.claraboia.bibleandroid.models

import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import com.claraboia.bibleandroid.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.BIB_FOLDER
import com.claraboia.bibleandroid.getBibleStream
import java.util.*

/**
 * Created by lucas.batagliao on 12/07/2016.
 */
class Bible() {


    var title: String = ""

    var books: MutableList<Book> = ArrayList()

    companion object {
        fun load(name: String): Bible {
            val filename = "$name${BIB_FILE_EXTENSION}"

            val stream = getBibleStream(filename)
            stream.use {
                return BibleSaxParser().parse(it)
            }
        }
    }

}