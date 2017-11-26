package com.claraboia.bibleandroid.helpers

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.SuperscriptSpan
import android.text.style.TextAppearanceSpan
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.BuildConfig
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.*
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Created by lucas.batagliao on 26/09/2016.
 */


//extension methods for Bible type
//TODO: move to TextRepository
fun Bible.getAddressText(context: Context, address: BibleAddress): SpannableStringBuilder {
    val chapter = this.books[address.bookOrder -1].chapters[address.chapterOrder -1]

    val builder = SpannableStringBuilder()

    var start = 0
    var end = 0
    //val text = StringBuilder()
    for (v in chapter.verses) {
        start = end

        //verse num
        val verseNumSpan = TextAppearanceSpan(context, R.style.VerseNumberTextAppearance)
        val vnum = v.verseOrder.toString() + " "
        end = start + vnum.length
        builder.append(vnum)
        builder.setSpan(SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(verseNumSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        //verse text
        val verseSpan = TextAppearanceSpan(context, R.style.VerseTextAppearance)
        val vtext = v.text + " \n"
        start = end
        end = start + vtext.length
        builder.append(vtext)
        builder.setSpan(verseSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    }
    //return text.toString()
    return builder
}

