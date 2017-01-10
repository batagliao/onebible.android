package com.claraboia.bibleandroid.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by lucasbatagliao on 25/10/16.
 */
class BibleTranslation : Parcelable {

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

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(abbreviation)
        dest?.writeByte(if (active) 1 else 0)
        dest?.writeString(file)
        dest?.writeDouble(fileSize)
        dest?.writeString(format)
        dest?.writeString(language)
        dest?.writeString(name)
        dest?.writeString(version)
        dest?.writeString(localFile)
    }

    override fun describeContents(): Int {
        return 0
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