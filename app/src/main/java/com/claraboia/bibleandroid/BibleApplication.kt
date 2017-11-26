package com.claraboia.bibleandroid

import android.app.Application
import android.content.Intent
import com.claraboia.bibleandroid.activities.DispatchActivity
import com.claraboia.bibleandroid.infrastructure.Preferences
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.BibleAddress
import com.claraboia.bibleandroid.models.BibleTranslation
import com.claraboia.bibleandroid.viewmodels.BookForSort
import com.google.firebase.auth.FirebaseUser

class BibleApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: BibleApplication
    }

    lateinit var preferences: Preferences
    lateinit var currentBible: Bible

    var currentUser: FirebaseUser? = null

    var currentBook = 0
    var currentChapter = 0
    val booksForSelection: MutableList<BookForSort> = ArrayList()
    val localBibles: MutableList<BibleTranslation> = ArrayList()

    val currentAddress: BibleAddress
        get() {
            val address = BibleAddress()
            address.bookOrder = currentBook
            address.chapterOrder = currentChapter
            return address
        }

    override fun onCreate() {
        super.onCreate()

        preferences = Preferences(this)
    }

    fun updatePosition(address: BibleAddress){
        currentBook = address.bookOrder
        currentChapter = address.chapterOrder
        preferences.lastAccessedAddress = address
    }

    fun doRestart() {
        val intent = Intent(this, DispatchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}