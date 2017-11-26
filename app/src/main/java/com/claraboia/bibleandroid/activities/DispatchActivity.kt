package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.extensions.bibleApplication
import com.claraboia.bibleandroid.helpers.getBookType
import com.claraboia.bibleandroid.repositories.BibleRepository
import com.claraboia.bibleandroid.viewmodels.BookForSort
import com.google.firebase.auth.FirebaseAuth

class DispatchActivity : AppCompatActivity() {

    private lateinit var firebaseauth: FirebaseAuth
    private val REQUEST_GOOGLE_PLAY_SERVICES = 90909

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: get this code back
        //verifyGooglePlay()

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

                performStartupPath()
            }
        }else{
            bibleApplication.currentUser = firebaseauth.currentUser
            performStartupPath()
        }
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

    private fun performStartupPath(){

        bibleApplication.localBibles.addAll(BibleRepository.getAvailableBiblesLocal())

        if(bibleApplication.localBibles.size == 0 || bibleApplication.preferences.selectedTranslation.isEmpty()) {

            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.translationNeededToStart)
            builder.setPositiveButton(R.string.ok) { dialog, button ->
                val intent = Intent(this, SelectTranslationActivity::class.java)
                intent.putExtra(SHOULD_OPEN_CLOUD_TAB_KEY, true)
                startActivity(intent)
                finish()
            }

            val dialog = builder.create()
            dialog.show()

        }else{
            loadCurrentBible()
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent)
            finish()
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


}
