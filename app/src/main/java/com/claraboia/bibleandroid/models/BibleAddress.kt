package com.claraboia.bibleandroid.models

import android.os.Parcel
import android.os.Parcelable
import com.claraboia.bibleandroid.infrastructure.KParcelable

/**
 * Created by lucas.batagliao on 21/11/2017.
 */
class BibleAddress : KParcelable {
    var bookOrder: Int = 1
    var chapterOrder: Int = 1
    var verses: HashSet<Int> = HashSet()

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(bookOrder)
        writeInt(chapterOrder)
        writeIntArray(verses.toIntArray())
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<BibleAddress> = object : Parcelable.Creator<BibleAddress> {
            override fun createFromParcel(source: Parcel): BibleAddress {
                val address = BibleAddress()
                address.bookOrder = source.readInt()
                address.chapterOrder = source.readInt()
                val array: IntArray = intArrayOf()
                source.readIntArray(array)
                address.verses = array.toHashSet()
                return address
            }

            override fun newArray(size: Int): Array<BibleAddress> {
                return kotlin.emptyArray()
            }
        }
    }
}