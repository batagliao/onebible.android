package com.claraboia.bibleandroid.utils

import com.claraboia.bibleandroid.BuildConfig
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

val SELECTED_TRANSLATION_KEY: String
    get() = "selectedTranslation"

val LAST_ACCESSED_ADDRESS_KEY: String
    get() = "lastAccessedAddress"

val BIB_FILE_EXTENSION: String
    get() = ".bib"

val BIB_FOLDER: String
    get() = "bibs"


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