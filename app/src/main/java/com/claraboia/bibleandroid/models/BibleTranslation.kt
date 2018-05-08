package com.claraboia.bibleandroid.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.claraboia.bibleandroid.infrastructure.KParcelable

/**
 * Created by lucas.batagliao on 21/11/2017.
 */
@Entity(tableName = "translation")
class BibleTranslation : KParcelable {
    @PrimaryKey(autoGenerate = false)
    var abbreviation = ""

    @ColumnInfo()
    var active = true

    @ColumnInfo()
    var file = ""

    @ColumnInfo()
    var localFile = ""

    @ColumnInfo()
    var fileSize = 0.0

    @ColumnInfo()
    var format = "xml"

    @ColumnInfo()
    var language = ""

    @ColumnInfo()
    var name = ""

    @ColumnInfo()
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