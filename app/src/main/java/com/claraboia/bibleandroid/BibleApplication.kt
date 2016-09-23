package com.claraboia.bibleandroid

import android.app.Application
import android.content.Context
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.utils.Preferences


//make an extended property for context
val Context.bibleApplication: BibleApplication
    get() = applicationContext as BibleApplication


class BibleApplication : Application() {

    lateinit var preferences: Preferences
    lateinit var currentBible: Bible

    override fun onCreate() {
        super.onCreate()

        preferences = Preferences(this)

        //TODO: treat exception
        currentBible = Bible.load(preferences.selectedTranslation)

    }
}