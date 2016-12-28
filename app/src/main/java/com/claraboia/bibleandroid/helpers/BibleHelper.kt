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

// storage consts
const val BIB_FILE_EXTENSION: String = ".bib"
const val BIB_FOLDER: String = "bibs"
const val BIB_LOCAL_FILE = "bibles.local"

fun loadBible(translation: BibleTranslation): Bible {
    val filename = translation.getFileName()

    val stream = getBibleStream(filename)
    stream.use {
        return BibleSaxParser().parse(it)
    }
}

fun getBibleDir(): String {
    val bibdir = File("/data/data/${BuildConfig.APPLICATION_ID}/files/$BIB_FOLDER/")

    if (!bibdir.exists())
        bibdir.mkdirs()

    return bibdir.absolutePath
}

private fun getLocalBiblesFile(): File {
    val biblefile = File("${getBibleDir()}/$BIB_LOCAL_FILE")
    return biblefile
}

fun getBibleStream(name: String): FileInputStream {
    val path = "${getBibleDir()}/$name"
    val file = File(path)

    return FileInputStream(file)
}

fun getAvailableBiblesLocal(): List<BibleTranslation> {

    val biblefile = getLocalBiblesFile()
    var bibles: ArrayList<BibleTranslation> = ArrayList()

    if(!biblefile.exists()) return bibles

    val gson = Gson()
    val type = object: TypeToken<List<BibleTranslation>>(){}.type

    val json = biblefile.readText()
    bibles = gson.fromJson(json, type)

//    dir.listFiles().forEach {
//
//        if(BIB_FILE_EXTENSION.endsWith(it.extension, true)) {
//            val bible = BibleTranslation()
//
//            //TODO: improve
//            //TODO: how to store name and language
//            //{abbreviation}.{version}.bib
//
//            val namestrip = it.name.split(".")
//            val abbreviation = namestrip[0]
//            val version = namestrip[1]
//
//            bible.name = it.name
//            bible.abbreviation = abbreviation
//            bible.file = it.absolutePath
//            bible.fileSize = it.length().toDouble()
//            bible.format = "xml"
//            bible.version = version
//
//            bibles.add(bible)
//        }
//    }
    return bibles
}

//extension methods for Bible type
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




fun BibleTranslation.getFileName() : String{
    //return "${this.abbreviation}.${this.version}$BIB_FILE_EXTENSION"
    return "${this.abbreviation}$BIB_FILE_EXTENSION"
}

fun saveLocalTranslations(translations: List<BibleTranslation>){
    val gson = Gson()
    val json = gson.toJson(translations)
    val biblefile = getLocalBiblesFile()
    biblefile.writeText(json)
}