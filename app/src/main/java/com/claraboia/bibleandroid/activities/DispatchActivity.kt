package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.os.Bundle
import android.os.Trace
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.extensions.bibleApplication
import com.claraboia.bibleandroid.infrastructure.FirstRun
import com.claraboia.bibleandroid.infrastructure.Preferences
import com.claraboia.bibleandroid.models.BibleTranslation
import com.claraboia.bibleandroid.repositories.BibleRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class DispatchActivity : AppCompatActivity() {

    private lateinit var firebaseauth: FirebaseAuth
    private val REQUEST_GOOGLE_PLAY_SERVICES = 90909

    private var firstRun: Int = FirstRun.NORMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: get this code back
        //verifyGooglePlay()

        firstRun = FirstRun.checkFirstRun()
        // defining loading text
        if(firstRun == FirstRun.FIRST_RUN){
            this.txtloading.text = getString(R.string.startup_message_first_time)
        }else{
            this.txtloading.text = getString(R.string.startup_message)
        }

        firebaseauth = FirebaseAuth.getInstance()
        if (firebaseauth.currentUser == null) {
            firebaseauth.signInAnonymously().addOnCompleteListener {
                Log.d("DispatchActivity", "signInAnonymously:onComplete:" + it.isSuccessful)

                bibleApplication.currentUser = firebaseauth.currentUser

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!it.isSuccessful) {
                    Log.w("DispatchActivity", "signInAnonymously", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }

                // performStartupPath()
            }
        } else {
            bibleApplication.currentUser = firebaseauth.currentUser
            // performStartupPath()
        }

        performStartupPath()
    }


//    private fun verifyGooglePlay(){
//        val playServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
//        if(playServicesStatus != ConnectionResult.SUCCESS){
//            //If google play services in not available show an error dialog and return
//            GoogleApiAvailability.getInstance().showErrorDialogFragment(this, playServicesStatus, 0)
//            //val errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(this, playServicesStatus, 0, null)
//            //errorDialog.show()
//            return
//        }
//    }

    private fun openDownloadScreen() {
        val intent = Intent(this, SelectTranslationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra(SHOULD_OPEN_CLOUD_TAB_KEY, true)
        startActivity(intent)
    }

    private fun performStartupPath() {



        Completable.fromAction {
            // bibleApplication.localBibles.addAll(BibleRepository.getAvailableBiblesLocal())
            // TODO: use SQLite to store the bibles

            if (firstRun == FirstRun.FIRST_RUN) {
                // copy default bible
                this.copyDefaultBibleTranslation()
                // mark it as selected
                val kjvTranslation = BibleTranslation()
                kjvTranslation.abbreviation = "kjv"
                kjvTranslation.active = true
                kjvTranslation.format = "XML"
                // kjvTranslation.file
                kjvTranslation.localFile = BibleRepository.getBiblePath(kjvTranslation.abbreviation)
                kjvTranslation.language = "en-US"
                // kjvTranslation.fileSize =
                kjvTranslation.name = "King James Version"
                kjvTranslation.version = "1.0"
                BibleApplication.instance.preferences.selectedTranslation = kjvTranslation
            }

//            if (bibleApplication.localBibles.size == 0 || bibleApplication.preferences.selectedTranslation.isEmpty()) {
//
//                val builder = AlertDialog.Builder(this)
//                builder.setMessage(R.string.translationNeededToStart)
//                builder.setPositiveButton(R.string.ok) { dialog, button ->
//                    //openDownloadScreen()
//                    finish()
//                }
//                builder.setOnDismissListener { openDownloadScreen() }
//
//                val dialog = builder.create()
//                dialog.show()
//
//            } else {


            /// MEASURING TIME
//        var durationSum : Long = 0
//        var durationmax : Long = 0
//        var durationmin = Long.MAX_VALUE
//        for (i in 1..1000) {
//            val starttime = System.nanoTime()
            loadCurrentBible()

//            val endtime = System.nanoTime()
//            val duration = endtime - starttime
//
//            if(duration > durationmax) durationmax = duration
//            if(duration < durationmin) durationmin = duration
//            durationSum += duration
//            Log.d("DURATION:", duration.toString())
//        }
//        Log.d("AVERAGE", (durationSum / 1000).toString())
//        Log.d("MIN", durationmin.toString())
//        Log.d("MAX", durationmax.toString())


//        val intent = Intent(this, ReadActivity::class.java)
            // startActivity(intent)
            // finish()
//            }
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            // move to another activity
            val intent = Intent(this, ReadActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
            overridePendingTransition(0,0)
        }
    }

    private fun loadCurrentBible() {
        bibleApplication.currentBible = BibleRepository.loadBible(bibleApplication.preferences.selectedTranslation)

        //load last accessed address as current position
        val lastAddress = bibleApplication.preferences.lastAccessedAddress
        bibleApplication.currentBook = lastAddress.bookOrder
        bibleApplication.currentChapter = lastAddress.chapterOrder

        //TODO: review later
//        //create a bookCollection to be able to sort, change without affect orignal list
//        bibleApplication.currentBible.books.forEach { b ->
//            val newbook = BookForSort(
//                    b.bookOrder,
//                    b.chapters.size,
//                    b.getBookName(),
//                    b.getBookAbbrev(),
//                    b.getBookType()
//            )
//            bibleApplication.booksForSelection.add(newbook)
//        }
    }


    private fun copyDefaultBibleTranslation() {
        val defaultbible = getString(R.string.defaultbible)
        val filename = BibleRepository.getTranslationFileName(defaultbible)
        Log.d("FILENAME", filename)
        //val list = assets.list("")
        assets.open(filename).use {
            val outfile = File(BibleRepository.getBiblePath(defaultbible))
            it.copyTo(outfile.outputStream())
        }
    }
}
