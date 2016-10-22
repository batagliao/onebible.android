package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.*

/**
 * Created by lucas.batagliao on 26/09/2016.
 */

private const val BOOK_NAME_KEY : String = "Book%d"
private const val BOOK_SUMMARY_KEY = "%sSummary"
private const val BOOK_KEY : String = "KeyBook%d"
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

//extension methods for Book type
fun Book.getKey(): String {
    val resourceName = String.format(BOOK_KEY, this.bookOrder)
    return ResourceHelper.getStringByName(resourceName)
}

fun Book.getSummary(): String{
    val resourceName = String.format(BOOK_SUMMARY_KEY, this.getKey())
    return ResourceHelper.getStringByName(resourceName)
}

fun Book.getBookName() : String{
    return getBookName(this.bookOrder)
}

fun getBookName(bookOrder: Int) : String{
    val resourceName = String.format(BOOK_NAME_KEY, bookOrder)
    return ResourceHelper.getStringByName(resourceName)
}

fun Book.getBookAbbrev(): String {
    return getBookAbbrev(this.bookOrder)
}

fun getBookAbbrev(bookOrder: Int): String{
    val resourceName = String.format(BOOK_ABBREV_KEY, bookOrder)
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

//extension methods from BibleAddress type
fun BibleAddress.asFullText(): String {
    val bookname = getBookName(this.bookOrder)

    var result: String = "$bookname ${this.chapterOrder}"

    return result
}

fun BibleAddress.asAbbreviatedText() : String{
    val bookname = getBookAbbrev(this.bookOrder)

    var result: String = "$bookname ${this.chapterOrder}"

    return result
}