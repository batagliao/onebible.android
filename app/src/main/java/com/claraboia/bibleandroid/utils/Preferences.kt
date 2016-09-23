package com.claraboia.bibleandroid.utils

import android.content.Context
import android.content.ContextWrapper
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.BibleAddress
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

// preferences consts
private const val SELECTED_TRANSLATION_KEY: String = "selectedTranslation"

private const val LAST_ACCESSED_ADDRESS_KEY: String = "lastAccessedAddress"

class Preferences(private val context: Context ) {
    init {
       Prefs.Builder()
                .setContext(context)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(context.packageName)
                .setUseDefaultSharedPreference(true)
                .build()
    }

    private val defaultTranslation = getResource(R.string.defaultTranslation)

    var selectedTranslation: String
        get() = Prefs.getString(SELECTED_TRANSLATION_KEY, defaultTranslation)
        set(value) = Prefs.putString(SELECTED_TRANSLATION_KEY, value)

    var lastAccessedAddress: BibleAddress
        get() {
            val json = Prefs.getString(LAST_ACCESSED_ADDRESS_KEY, "")
            if(json == ""){
                return BibleAddress()
            }else{
               val gson = Gson()
                return gson.fromJson(json, BibleAddress::class.java)
            }
        }
    set(value) {
        val gson = Gson()
        val json = gson.toJson(value)
        Prefs.putString( LAST_ACCESSED_ADDRESS_KEY, json)
    }

    private fun getResource(id: Int): String {
        return this.context.resources.getString(id)
    }





}