package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.getBookAbbrev
import com.claraboia.bibleandroid.helpers.getBookName
import com.claraboia.bibleandroid.helpers.getBookType
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.viewmodels.BookForSort

class DispatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadBible()

        val intent = Intent(this, ReadActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadBible() {
        bibleApplication.currentBible = Bible.load(bibleApplication.preferences.selectedTranslation)

        //load last accessed address as current position
        val lastAddress = bibleApplication.preferences.lastAccessedAddress
        bibleApplication.currentBook = lastAddress.bookOrder
        bibleApplication.currentChapter = lastAddress.chapterOrder

        //create a bookCollection to be able to sort, change without affect orignal list
        bibleApplication.currentBible.books.forEach { b ->
            val newbook = BookForSort(
                    b.bookOrder,
                    b.chapters.size,
                    b.getBookName(),
                    b.getBookAbbrev(),
                    b.getBookType()
            )
            bibleApplication.booksForSelection.add(newbook)
        }
    }
}
