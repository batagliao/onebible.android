package com.claraboia.bibleandroid

import android.app.Application
import android.content.Context
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.utils.Preferences


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

    override fun onCreate() {
        super.onCreate()

        preferences = Preferences(this)

        //TODO: treat exception
        currentBible = Bible.load(preferences.selectedTranslation)

    }

    companion object{
        lateinit var instance : BibleApplication
    }
}