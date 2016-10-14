package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.*

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

fun Book.getTestament() : TestamentEnum{
    if(bookOrder < 40){
        return TestamentEnum.OLD_TESTAMENT
    }
    return TestamentEnum.NEW_TESTAMENT
}

fun Book.getBookType() : BookTypeEnum{
    if (getTestament() == TestamentEnum.OLD_TESTAMENT)
    {
        if (bookOrder <= 5)
            return BookTypeEnum.PENTATEUCH
        if (bookOrder <= 17)
            return BookTypeEnum.HISTORIC
        if (bookOrder <= 22)
            return BookTypeEnum.POETIC
        //if (BookOrder <= 39)
        return BookTypeEnum.PROPHETIC
    }

    //new testament
    if (bookOrder <= 43)
        return BookTypeEnum.GOSPEL
    if (bookOrder <= 65)
        return BookTypeEnum.EPISTLE
    return BookTypeEnum.PROPHETIC //66
}
