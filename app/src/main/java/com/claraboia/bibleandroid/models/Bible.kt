package com.claraboia.bibleandroid.models

import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import com.claraboia.bibleandroid.utils.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.utils.BIB_FOLDER
import com.claraboia.bibleandroid.utils.getBibleStream
import java.util.*

/**
 * Created by lucas.batagliao on 12/07/2016.
 */
class Bible() {


    var Title: String = ""

    var Books: MutableList<Book> = ArrayList()

    companion object {
        fun load(name: String): Bible {
            var filename = "$BIB_FOLDER/$name$BIB_FILE_EXTENSION"

            val stream = getBibleStream(name)
            stream.use {
                return BibleSaxParser().parse(it)
            }
        }
    }

}