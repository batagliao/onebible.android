package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.BibleAddress
import com.claraboia.bibleandroid.models.Book

/**
 * Created by lucas.batagliao on 26/09/2016.
 */

private const val BOOK_NAME_KEY : String = "Book%d"
private const val BOOK_ABBREV_KEY : String = "BookAbbrev%d"


//extension methods for Bible type
fun Bible.getAddressText(address: BibleAddress) : String {

    val chapter = this.books[address.bookOrder].chapters[address.chapterOrder]

    val text = StringBuilder()

    for (v  in chapter.verses){
        text.appendln("${v.verseOrder.toString()}. ${v.text}")
    }
    return text.toString()
}

fun Book.getBookName() : String{
    val resourceName = String.format(BOOK_NAME_KEY, this.bookOrder)
    return ResourceHelper.getStringByName(resourceName)
}

fun Book.getBookAbbrev(): String {
    val resourceName = String.format(BOOK_ABBREV_KEY, this.bookOrder)
    return ResourceHelper.getStringByName(resourceName)
}