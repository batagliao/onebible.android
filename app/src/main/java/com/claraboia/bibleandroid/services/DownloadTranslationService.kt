package com.claraboia.bibleandroid.services

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.claraboia.bibleandroid.helpers.BIB_FILE_EXTENSION
import com.claraboia.bibleandroid.helpers.getBibleDir
import com.claraboia.bibleandroid.models.BibleTranslation
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.FileOutputStream
import java.net.URL
import java.net.URLConnection

/**
 * Created by lucas.batagliao on 01-Nov-16.
 */

const val EXTRA_TRANSLATION = "com.claraboia.bibleandroid.BibleTranslation.extra"
const val DOWNLOAD_TRANSLATION_PROGRESS_ACTION = "com.claraboia.bibleandroid.DownloadTranslation.ActionProgress"
const val DOWNLOAD_TRANSLATION_PROGRESS_VALUE = "com.claraboia.bibleandroid.DownloadTranslation.ProgressValue"

class DownloadTranslationService : IntentService("DownloadTranslationService") {
    override fun onHandleIntent(intent: Intent?) {
        val translation = intent?.getParcelableExtra<BibleTranslation>(EXTRA_TRANSLATION)
        val destfilepath = getBibleDir() + "/${translation?.abbreviation}_${translation?.version}.$BIB_FILE_EXTENSION"

        downloadFile(translation?.file!!, destfilepath)


        //TODO: notify on status bar when download finishes
    }

    private fun downloadFile(source: String, target: String){
        var count = 0
//        try {

        val url = URL(source)

        val connection = url.openConnection()
        connection.connect()

        // this will be useful so that you can show a tipical 0-100%
        // progress bar
        val lenghtOfFile = connection.contentLength

        // download the file
        val input = BufferedInputStream(url.openStream(), 8192)

        // Output stream
        val output = FileOutputStream(target)

        val data = ByteArray(1024)

        var total: Long = 0

        do {
            count = input.read(data)
            total += count
            // publishing the progress....
            // After this onProgressUpdate will be called
            val progress = (total*100/lenghtOfFile).toInt()
            postProgress(progress)

            // writing data to file
            output.write(data, 0, count)

            // flushing output
            output.flush()

            // closing streams
            output.close()
            input.close()
        } while (count > 0)


//        } catch (e: Exception) {
//            Log.e("Error: ", e.message)
//        }
    }

    fun postProgress(progress: Int){
        val intent = Intent(DOWNLOAD_TRANSLATION_PROGRESS_ACTION)
            .putExtra(DOWNLOAD_TRANSLATION_PROGRESS_VALUE, progress)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}