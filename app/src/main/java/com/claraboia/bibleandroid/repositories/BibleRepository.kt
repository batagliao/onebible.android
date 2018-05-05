package com.claraboia.bibleandroid.repositories

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.BuildConfig
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.BibleTranslation
import com.claraboia.bibleandroid.models.parsers.BibleSaxParser
import com.claraboia.bibleandroid.models.parsers.BibleVTDParser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.*

// storage consts
const val BIB_FILE_EXTENSION: String = ".bib"
const val BIB_FOLDER: String = "bibs"
const val BIB_LOCAL_FILE = "bibles.local"


object BibleRepository {


    fun loadBible(translation: BibleTranslation): Bible {
        val filename = getTranslationFileName(translation)

        val stream = getBibleFile(filename).inputStream()
        stream.use {
            //return BibleVTDParser.parse(it)
             return BibleSaxParser().parse(it)
        }
    }

    private fun getBibleDir(): String {
        val bibdir = File("/data/data/${BuildConfig.APPLICATION_ID}/files/${BIB_FOLDER}/")

        if (!bibdir.exists())
            bibdir.mkdirs()

        return bibdir.absolutePath
    }

    fun getBiblePath(translation: BibleTranslation): String {
        val fname = getTranslationFileName(translation)
        return getBibleDir() + "/$fname"
    }

    fun getBiblePath(translation: String): String {
        val fname = getTranslationFileName(translation)
        return getBibleDir() + "/$fname"
    }

    private fun getLocalBiblesFile(): File {
        return File("${getBibleDir()}/$BIB_LOCAL_FILE")
    }

//    private fun getBibleInputStream(name: String): FileInputStream {
//        val path = "${getBibleDir()}/$name"
//        val file = File(path)
//
//        return FileInputStream(file)
//    }

    private fun getBibleFile(translationfilename: String): File{
        val path = "${getBibleDir()}/$translationfilename"
        return File(path)
    }

    fun getAvailableBiblesLocal(): List<BibleTranslation> {

        val biblefile = getLocalBiblesFile()
        var bibles: ArrayList<BibleTranslation> = ArrayList()

        if (!biblefile.exists()) return bibles

        val gson = Gson()
        val type = object : TypeToken<List<BibleTranslation>>() {}.type

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

    fun getTranslationFileName(translastion: BibleTranslation): String {
        return getTranslationFileName(translastion.abbreviation)
    }

    fun getTranslationFileName(translation: String): String{
        return "$translation$BIB_FILE_EXTENSION"
    }

    fun addLocalTranslation(translation: BibleTranslation) {
        BibleApplication.instance.localBibles.add(translation)
        saveLocalTranslations(BibleApplication.instance.localBibles)
    }

    fun removeLocalTranslation(translation: BibleTranslation) {
        BibleApplication.instance.localBibles.remove(translation)
        saveLocalTranslations(BibleApplication.instance.localBibles)
    }

    private fun saveLocalTranslations(translations: List<BibleTranslation>) {
        val gson = Gson()
        val json = gson.toJson(translations)
        val biblefile = getLocalBiblesFile()
        biblefile.writeText(json)
    }
}
