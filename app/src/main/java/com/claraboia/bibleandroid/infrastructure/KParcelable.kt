package com.claraboia.bibleandroid.infrastructure

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by lucas.batagliao on 21/11/2017.
 */
interface KParcelable : Parcelable {
    override fun describeContents() = 0
    override fun writeToParcel(dest: Parcel, flags: Int)
}