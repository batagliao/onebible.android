package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.*
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.viewmodels.BookForSort
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.auth.FirebaseAuth

class DispatchActivity : AppCompatActivity() {

    private lateinit var firebaseauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verifyGooglePlay()

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

    private fun verifyGooglePlay(){
        val playServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if(playServicesStatus != ConnectionResult.SUCCESS){
            //If google play services in not available show an error dialog and return
            val errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(this, playServicesStatus, 0, null)
            errorDialog.show()
            return
        }
    }

    private fun performStartupPath(){
        //TODO: verify which bibles exists locally
        val bibles = getAvailableBiblesLocal()
        if(bibles.size == 0) {
            //TODO: go to select translation screen
        }else{
            loadCurrentBible()
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadCurrentBible() {
        bibleApplication.currentBible = loadBible(bibleApplication.preferences.selectedTranslation)

        //load last accessed address as current position
        val lastAddress = bibleApplication.preferences.lastAccessedAddress
        bibleApplication.currentBook = lastAddress.bookOrder
        bibleApplication.currentChapter = lastAddress.chapterOrder

        //create a bookCollection to be able to sort, change without affect orignal list
        bibleApplication.currentBible.books.forEach { b ->
            val newbook = BookForSort(
                    b.bookOrder,
                    b.chapters.size,
                    b.getBookName(),
                    b.getBookAbbrev(),
                    b.getBookType()
            )
            bibleApplication.booksForSelection.add(newbook)
        }
    }


}
