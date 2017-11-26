package com.claraboia.bibleandroid.extensions

import android.content.Context
import com.claraboia.bibleandroid.BibleApplication


val Context.bibleApplication: BibleApplication
    get() = applicationContext as BibleApplication