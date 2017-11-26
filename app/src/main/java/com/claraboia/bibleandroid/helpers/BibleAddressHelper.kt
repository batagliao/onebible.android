package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.BibleAddress
import com.claraboia.bibleandroid.models.Book

/**
 * Created by lucasbatagliao on 03/12/16.
 */

fun isLastChapter(book: Book, chapter: Int): Boolean {
    val bookSize = book.chapters.size
    return chapter == bookSize
}

fun isLastBook(bible: Bible, book: Int): Boolean {
    val bibleSize = bible.books.size
    return book == bibleSize
}