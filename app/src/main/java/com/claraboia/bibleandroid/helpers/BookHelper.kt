package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.models.Book
import com.claraboia.bibleandroid.models.BookTypeEnum
import com.claraboia.bibleandroid.models.TestamentEnum

/**
 * Created by lucasbatagliao on 25/10/16.
 */


private const val BOOK_KEY : String = "KeyBook%d"

//extension methods for Book type
fun Book.getKey(): String {
    val resourceName = String.format(BOOK_KEY, this.bookOrder)
    return ResourceHelper.getStringByName(resourceName)
}

fun Book.getTestament(): TestamentEnum {
    if (bookOrder < 40) {
        return TestamentEnum.OLD_TESTAMENT
    }
    return TestamentEnum.NEW_TESTAMENT
}

fun Book.getBookType(): BookTypeEnum {
    if (getTestament() == TestamentEnum.OLD_TESTAMENT) {
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