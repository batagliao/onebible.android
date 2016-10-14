package com.claraboia.bibleandroid.models

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.helpers.getBookType

/**
 * Created by lucas.batagliao on 12/07/2016.
 */

enum class BookTypeEnum {
    PENTATEUCH,
    HISTORIC,
    POETIC,
    PROPHETIC,
    GOSPEL,
    EPISTLE;

    override fun toString(): String {
        val resource = BibleApplication.instance.resources
        when (this) {
            PENTATEUCH -> return resource.getString(R.string.Pentateuch)
            HISTORIC -> return resource.getString(R.string.Historic)
            POETIC -> return resource.getString(R.string.Poetic)
            PROPHETIC -> return resource.getString(R.string.Prophetic)
            GOSPEL -> return resource.getString(R.string.Gospel)
            EPISTLE -> return resource.getString(R.string.Epistle)
        }
    }

    fun color(): ColorDrawable {
        val resource = BibleApplication.instance.resources
        when (this) {
            PENTATEUCH -> return ColorDrawable(resource.getColor(R.color.colorPentateuch, null))
            HISTORIC -> return ColorDrawable(resource.getColor(R.color.colorHistoric, null))
            POETIC -> return ColorDrawable(resource.getColor(R.color.colorPoetic, null))
            PROPHETIC -> return ColorDrawable(resource.getColor(R.color.colorProphetic, null))
            GOSPEL -> return ColorDrawable(resource.getColor(R.color.colorGospel, null))
            EPISTLE -> return ColorDrawable(resource.getColor(R.color.colorEpistle, null))
        }
    }
}