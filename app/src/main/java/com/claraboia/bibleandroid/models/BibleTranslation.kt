package com.claraboia.bibleandroid.models

import android.os.Parcel
import android.os.Parcelable
import com.claraboia.bibleandroid.infrastructure.KParcelable

/**
 * Created by lucas.batagliao on 21/11/2017.
 */
class BibleTranslation : KParcelable {
    var abbreviation = ""
    var active = true
    var file = ""
    var localFile = ""
    var fileSize = 0.0
    var format = "xml"
    var language = ""
    var name = ""
    var version = "1.0"

    fun isEmpty() : Boolean = abbreviation == ""

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(abbreviation)
        writeByte(if (active) 1 else 0)
        writeString(file)
        writeDouble(fileSize)
        writeString(format)
        writeString(language)
        writeString(name)
        writeString(version)
        writeString(localFile)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<BibleTranslation> = object : Parcelable.Creator<BibleTranslation> {
            override fun createFromParcel(source: Parcel): BibleTranslation {
                val translation = BibleTranslation()
                translation.abbreviation = source.readString()
                translation.active = source.readByte() > 0
                translation.file = source.readString()
                translation.fileSize = source.readDouble()
                translation.format = source.readString()
                translation.language = source.readString()
                translation.name = source.readString()
                translation.version = source.readString()
                translation.localFile = source.readString()
                return translation
            }

            override fun newArray(size: Int): Array<BibleTranslation> {
                return kotlin.emptyArray()
            }
        }
    }
}