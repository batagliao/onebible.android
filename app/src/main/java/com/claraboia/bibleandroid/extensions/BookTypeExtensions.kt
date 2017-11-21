package com.claraboia.bibleandroid.extensions

import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.BookTypeEnum

/**
 * Created by lucas.batagliao on 21/11/2017.
 */

fun BookTypeEnum.color() : ColorDrawable {
    val ctx = BibleApplication.instance.applicationContext
    return when (this) {
        BookTypeEnum.PENTATEUCH -> ColorDrawable(ContextCompat.getColor(ctx, R.color.colorPentateuch))
        BookTypeEnum.HISTORIC -> ColorDrawable(ContextCompat.getColor(ctx, R.color.colorHistoric))
        BookTypeEnum.POETIC -> ColorDrawable(ContextCompat.getColor(ctx, R.color.colorPoetic))
        BookTypeEnum.PROPHETIC -> ColorDrawable(ContextCompat.getColor(ctx, R.color.colorProphetic))
        BookTypeEnum.GOSPEL -> ColorDrawable(ContextCompat.getColor(ctx, R.color.colorGospel))
        BookTypeEnum.EPISTLE -> ColorDrawable(ContextCompat.getColor(ctx, R.color.colorEpistle))
    }
}

fun BookTypeEnum.name(): String{
    val resource = BibleApplication.instance.resources
    return when (this) {
        BookTypeEnum.PENTATEUCH -> resource.getString(R.string.Pentateuch)
        BookTypeEnum.HISTORIC -> resource.getString(R.string.Historic)
        BookTypeEnum.POETIC -> resource.getString(R.string.Poetic)
        BookTypeEnum.PROPHETIC -> resource.getString(R.string.Prophetic)
        BookTypeEnum.GOSPEL -> resource.getString(R.string.Gospel)
        BookTypeEnum.EPISTLE -> resource.getString(R.string.Epistle)
    }
}