package com.claraboia.bibleandroid.utils

import android.content.Context
import android.content.ContextWrapper
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.BibleAddress
import com.claraboia.bibleandroid.views.BooksSelectDisplay
import com.claraboia.bibleandroid.views.BooksSelectSortOrder
import com.claraboia.bibleandroid.views.BooksSelectSortType
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

// preferences consts
private const val SELECTED_TRANSLATION_KEY = "selectedTranslation"
private const val LAST_ACCESSED_ADDRESS_KEY = "lastAccessedAddress"
private const val BOOK_SELECTION_DISPLAY_TYPE = "BOOK_SELECTION_DISPLAY_TYPE"
private const val BOOK_SELECTION_SORT_TYPE = "BOOK_SELECTION_SORT_TYPE"
private const val BOOK_SELECTION_SORT_ORDER = "BOOK_SELECTION_SORT_ORDER"

class Preferences(private val context: Context) {
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
            if (json == "") {
                return BibleAddress()
            } else {
                val gson = Gson()
                return gson.fromJson(json, BibleAddress::class.java)
            }
        }
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            Prefs.putString(LAST_ACCESSED_ADDRESS_KEY, json)
        }

    var bookSelectionDisplayType: BooksSelectDisplay.BookLayoutDisplayType
        get() {
            val name = Prefs.getString(BOOK_SELECTION_DISPLAY_TYPE, BooksSelectDisplay.BookLayoutDisplayType.GRID.name)
            return BooksSelectDisplay.BookLayoutDisplayType.valueOf(name)
        }
        set(value) = Prefs.putString(BOOK_SELECTION_DISPLAY_TYPE, value.name)

    var bookSelectionSortType: BooksSelectSortType.BookSortType
        get() {
            val name = Prefs.getString(BOOK_SELECTION_SORT_TYPE, BooksSelectSortType.BookSortType.NORMAL.name)
            return BooksSelectSortType.BookSortType.valueOf(name)
        }
        set(value) = Prefs.putString(BOOK_SELECTION_SORT_TYPE, value.name)


    var bookSelectionSortOrder: BooksSelectSortOrder.BookSortOrder
        get() {
            val name = Prefs.getString(BOOK_SELECTION_SORT_ORDER, BooksSelectSortOrder.BookSortOrder.ASC.name)
            return BooksSelectSortOrder.BookSortOrder.valueOf(name)
        }
        set(value) = Prefs.putString(BOOK_SELECTION_SORT_ORDER, value.name)


    private fun getResource(id: Int): String {
        return this.context.resources.getString(id)
    }


}