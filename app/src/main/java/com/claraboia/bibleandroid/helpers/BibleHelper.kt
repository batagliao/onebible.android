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

fun loadBible(name: String): Bible {
    val filename = "$name$BIB_FILE_EXTENSION"

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
        val bible = BibleTranslation(
                name = it.name, //TODO: implement
                abbreviation = it.name, //TODO: implement
                file = it.absolutePath,
                language = "", //TODO: implement
                version = "" //TODO: implement
        )
        bibles.add(bible)
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