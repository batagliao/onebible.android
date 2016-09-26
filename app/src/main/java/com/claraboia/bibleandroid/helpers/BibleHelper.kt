package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.BibleAddress

/**
 * Created by lucas.batagliao on 26/09/2016.
 */

//extension methods for Bible type
fun Bible.getAddressText(address: BibleAddress) : String {

    val chapter = this.books[address.bookOrder].chapters[address.chapterOrder]

    val text = StringBuilder()

    for (v  in chapter.verses){
        text.appendln("${v.verseOrder.toString()}. ${v.text}")
    }
    return text.toString()
}