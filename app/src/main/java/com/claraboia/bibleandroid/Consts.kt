package com.claraboia.bibleandroid

import com.claraboia.bibleandroid.BuildConfig
import java.io.File
import java.io.FileInputStream
import java.io.InputStream



// storage consts
const val BIB_FILE_EXTENSION: String = ".bib"

const val BIB_FOLDER: String = "bibs"


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