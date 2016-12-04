package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.BibleAddress
import com.claraboia.bibleandroid.models.Book

/**
 * Created by lucasbatagliao on 03/12/16.
 */

//extension methods from BibleAddress type
fun BibleAddress.asFullText(): String {
    val bookname = getBookName(this.bookOrder)
    val result: String = "$bookname ${this.chapterOrder}"
    return result
}

fun BibleAddress.asAbbreviatedText(): String {
    val bookname = getBookAbbrev(this.bookOrder)
    val result: String = "$bookname ${this.chapterOrder}"
    return result
}

fun BibleAddress.previous(): BibleAddress {
    val bible = BibleApplication.instance.currentBible

    val newAddress = BibleAddress()
    newAddress.bookOrder = bookOrder
    newAddress.chapterOrder = chapterOrder

    if(chapterOrder == 1){
        if(bookOrder == 1){
            newAddress.bookOrder = 66
            newAddress.chapterOrder = bible.books[newAddress.bookOrder -1].chapters.size
        }else{
            newAddress.bookOrder -= 1
            newAddress.chapterOrder = bible.books[newAddress.bookOrder -1].chapters.size
        }
    }else{
        newAddress.chapterOrder -= 1
    }

    return newAddress
}

fun BibleAddress.next(): BibleAddress{
    val bible = BibleApplication.instance.currentBible
    val book = bible.books[bookOrder - 1]

    val newAddress = BibleAddress()
    newAddress.bookOrder = bookOrder
    newAddress.chapterOrder = chapterOrder

    if(isLastChapter(book, chapterOrder)){
        if(isLastBook(bible, bookOrder)){
            newAddress.bookOrder = 1
            newAddress.chapterOrder = 1
        }else{
            newAddress.bookOrder += 1
            newAddress.chapterOrder = 1
        }
    }else{
        newAddress.chapterOrder += 1
    }

    return newAddress
}

private fun isLastChapter(book: Book, chapter: Int): Boolean {
    val bookSize = book.chapters.size
    return chapter == bookSize
}

private fun isLastBook(bible: Bible, book: Int): Boolean {
    val bibleSize = bible.books.size
    return book == bibleSize
}