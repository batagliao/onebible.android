package com.claraboia.bibleandroid.repositories

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.helpers.*
import com.claraboia.bibleandroid.models.BibleAddress
import com.claraboia.bibleandroid.models.Book

private const val BOOK_SUMMARY_KEY = "%sSummary"
private const val BOOK_NAME_KEY: String = "Book%d"
private const val BOOK_ABBREV_KEY: String = "BookAbbrev%d"

object BibleTextRepository {

    fun getAdressFullText(address: BibleAddress): String {
        val bookname = getBookName(address.bookOrder)
        val result: String = "$bookname ${address.chapterOrder}"
        return result
    }

    fun adressAsAbbreviatedText(address: BibleAddress): String {
        val bookname = getBookAbbrev(address.bookOrder)
        val result: String = "$bookname ${address.chapterOrder}"
        return result
    }

    fun previousAddress(address: BibleAddress): BibleAddress {
        val bible = BibleApplication.instance.currentBible

        val newAddress = BibleAddress()
        newAddress.bookOrder = address.bookOrder
        newAddress.chapterOrder = address.chapterOrder

        if (address.chapterOrder == 1) {
            if (address.bookOrder == 1) {
                newAddress.bookOrder = 66
                newAddress.chapterOrder = bible.books[newAddress.bookOrder - 1].chapters.size
            } else {
                newAddress.bookOrder -= 1
                newAddress.chapterOrder = bible.books[newAddress.bookOrder - 1].chapters.size
            }
        } else {
            newAddress.chapterOrder -= 1
        }

        return newAddress
    }

    fun nextAddress(address: BibleAddress): BibleAddress {
        val bible = BibleApplication.instance.currentBible
        val book = bible.books[address.bookOrder - 1]

        val newAddress = BibleAddress()
        newAddress.bookOrder = address.bookOrder
        newAddress.chapterOrder = address.chapterOrder

        if (isLastChapter(book, address.chapterOrder)) {
            if (isLastBook(bible, address.bookOrder)) {
                newAddress.bookOrder = 1
                newAddress.chapterOrder = 1
            } else {
                newAddress.bookOrder += 1
                newAddress.chapterOrder = 1
            }
        } else {
            newAddress.chapterOrder += 1
        }

        return newAddress
    }

    fun getBookSummary(book: Book): String {
        val resourceName = String.format(BOOK_SUMMARY_KEY, book.getKey())
        return ResourceHelper.getStringByName(resourceName)
    }

    fun getBookName(book: Book): String {
        return getBookName(book.bookOrder)
    }

    fun getBookName(bookOrder: Int): String {
        val resourceName = String.format(BOOK_NAME_KEY, bookOrder)
        return ResourceHelper.getStringByName(resourceName)
    }


    fun getBookAbbrev(book: Book): String {
        return getBookAbbrev(book.bookOrder)
    }

    fun getBookAbbrev(bookOrder: Int): String {
        val resourceName = String.format(BOOK_ABBREV_KEY, bookOrder)
        return ResourceHelper.getStringByName(resourceName)
    }
}