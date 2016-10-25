package com.claraboia.bibleandroid

import android.app.Application
import android.content.Context
import com.claraboia.bibleandroid.helpers.getBookAbbrev
import com.claraboia.bibleandroid.helpers.getBookName
import com.claraboia.bibleandroid.helpers.getBookType
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.Book
import com.claraboia.bibleandroid.utils.Preferences
import com.claraboia.bibleandroid.viewmodels.BookForSort
import com.google.firebase.auth.FirebaseAuth
import java.util.*


//make an extended property for context
val Context.bibleApplication: BibleApplication
    get() = applicationContext as BibleApplication


class BibleApplication : Application() {
    init {
        instance = this
    }

    lateinit var preferences: Preferences
    lateinit var currentBible: Bible

    var currentBook = 0
    var currentChapter = 0

    val booksForSelection: MutableList<BookForSort> = ArrayList()

    override fun onCreate() {
        super.onCreate()

        preferences = Preferences(this)
    }



    companion object {
        lateinit var instance: BibleApplication
    }
}