package com.claraboia.bibleandroid.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by lucas.batagliao on 12/07/2016.
 */
class BibleAddress : Parcelable {


    var bookOrder: Int = 1
    var chapterOrder: Int = 1
    var verses: HashSet<Int> = HashSet()

    override fun describeContents(): Int {
        return 0;
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(bookOrder)
        dest?.writeInt(chapterOrder)
        dest?.writeIntArray(verses.toIntArray())
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