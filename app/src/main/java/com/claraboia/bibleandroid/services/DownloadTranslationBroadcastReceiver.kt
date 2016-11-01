package com.claraboia.bibleandroid.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by lucas.batagliao on 01-Nov-16.
 */
class DownloadTranslationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val progress = intent?.extras?.getInt(DOWNLOAD_TRANSLATION_PROGRESS_VALUE)
        Log.d("DOWNLOAD", "progress = $progress%")
        //TODO: implement
    }
}