package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.BuildConfig
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.*
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Created by lucas.batagliao on 26/09/2016.
 */

// storage consts
const val BIB_FILE_EXTENSION: String = ".bib"

const val BIB_FOLDER: String = "bibs"

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

fun getBibleStream(name: String): FileInputStream {
    val path = "${getBibleDir()}/$name"
    val file = File(path)

    return FileInputStream(file)
}

fun getAvailableBiblesLocal(): List<BibleTranslation> {
    val dir = File(getBibleDir())
    val bibles: ArrayList<BibleTranslation> = ArrayList()
    dir.listFiles().forEach {

        if(BIB_FILE_EXTENSION.endsWith(it.extension, true)) {
            val bible = BibleTranslation()

            //TODO: improve
            //TODO: how to store name and language
            //{abbreviation}.{version}.bib

            val namestrip = it.name.split(".")
            val abbreviation = namestrip[0]
            val version = namestrip[1]

            bible.name = it.name
            bible.abbreviation = abbreviation
            bible.file = it.absolutePath
            bible.fileSize = it.length().toDouble()
            bible.format = "xml"
            bible.version = version

            bibles.add(bible)
        }
    }
    return bibles
}

//extension methods for Bible type
fun Bible.getAddressText(address: BibleAddress): String {
    val chapter = this.books[address.bookOrder].chapters[address.chapterOrder]
    val text = StringBuilder()
    for (v in chapter.verses) {
        text.appendln("${v.verseOrder.toString()}. ${v.text}")
    }
    return text.toString()
}


//extension methods from BibleAddress type
fun BibleAddress.asFullText(): String {
    val bookname = getBookName(this.bookOrder)
    val result: String = "$bookname ${this.chapterOrder}"
    return result
}

fun BibleAddress.asAbbreviatedText(): String {
    val bookname = getBookAbbrev(this.bookOrder)
    val result: String = "$bookname ${this.chapterOrder}"
    return result
}

fun BibleTranslation.getFileName() : String{
    return "${this.abbreviation}.${this.version}$BIB_FILE_EXTENSION"
}