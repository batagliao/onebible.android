package com.claraboia.bibleandroid.services

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.*
import com.claraboia.bibleandroid.models.BibleTranslation
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

/**
 * Created by lucas.batagliao on 01-Nov-16.
 */

const val EXTRA_TRANSLATION = "com.claraboia.bibleandroid.BibleTranslation.extra"
const val DOWNLOAD_TRANSLATION_PROGRESS_ACTION = "com.claraboia.bibleandroid.DownloadTranslation.ActionProgress"
const val DOWNLOAD_TRANSLATION_PROGRESS_VALUE = "com.claraboia.bibleandroid.DownloadTranslation.ProgressValue"
const val DOWNLOAD_TRANSLATION_NAME_VALUE = "com.claraboia.bibleandroid.DownloadTranslation.NameValue"

class DownloadTranslationService : IntentService("DownloadTranslationService") {

    var notificationId = 0

    override fun onHandleIntent(intent: Intent?) {
        val translation = intent?.getParcelableExtra<BibleTranslation>(EXTRA_TRANSLATION)
        val destfilepath = getBibleDir() + "/${translation?.getFileName()}"
        translation?.localFile = destfilepath

        notify(translation!!)
        try {
            downloadFile(translation.file, destfilepath, translation)
            translation.addToLocalTranslations()

            //TODO: increase download count on Firebase
            postProgress(100, translation.abbreviation.toString())
        }finally {
            endNotification()
        }

        //TODO: notify on status bar when download finishes?


    }

    private fun downloadFile(source: String, target: String, translation: BibleTranslation?) {
        var count = 0
        try {
            val url = URL(source)

            val connection = url.openConnection()
            connection.connect()

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            // ---------
            // unfortunatelly we are getting -1 because size is unknow
            var lenghtOfFile = connection.contentLength
            if(lenghtOfFile <= 0){
                lenghtOfFile = tryGetFileSize(url)
            }

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
                val progress = (total * 100 / lenghtOfFile).toInt()
                postProgress(progress, translation?.abbreviation.toString())

                if(count > 0) {
                    // writing data to file
                    output.write(data, 0, count)
                }

            } while (count > 0)


            // flushing output
            output.flush()
            // closing streams
            output.close()
            input.close()



        } finally {

        }
    }

    private fun tryGetFileSize(url: URL): Int {
        var conn: HttpURLConnection? = null
        try {
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "HEAD"
            conn.inputStream
            return conn.contentLength
        } catch (e: IOException) {
            return -1
        } finally {
            conn!!.disconnect()
        }
    }

    fun postProgress(progress: Int, target: String) {
        val intent = Intent(DOWNLOAD_TRANSLATION_PROGRESS_ACTION)
                .putExtra(DOWNLOAD_TRANSLATION_PROGRESS_VALUE, progress)
                .putExtra(DOWNLOAD_TRANSLATION_NAME_VALUE, target)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun notify(translation : BibleTranslation){
        val text = resources.getText(R.string.notification_downloading).toString() + " ${translation.name}"

        val builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_bible_notify)
            .setContentTitle(translation.abbreviation)
            .setContentText(text)
            .setProgress(0, 0, true)

//        // Creates an explicit intent for an Activity in your app
//        val resultIntent = Intent(this, ResultActivity::class.java)
//
//// The stack builder object will contain an artificial back stack for the
//// started Activity.
//// This ensures that navigating backward from the Activity leads out of
//// your application to the Home screen.
//        val stackBuilder = TaskStackBuilder.create(this)
//// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ResultActivity::class.java)
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent)
//        val resultPendingIntent = stackBuilder.getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT)
//        mBuilder.setContentIntent(resultPendingIntent)

        notificationId = translation.hashCode()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, builder.build())
    }

    private fun endNotification(){
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(notificationId)
    }
}