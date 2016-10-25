package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.getBookAbbrev
import com.claraboia.bibleandroid.helpers.getBookName
import com.claraboia.bibleandroid.helpers.getBookType
import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.viewmodels.BookForSort
import com.google.firebase.auth.FirebaseAuth

class DispatchActivity : AppCompatActivity() {

    private lateinit var firebaseauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseauth = FirebaseAuth.getInstance()
        if (firebaseauth.currentUser == null) {
            firebaseauth.signInAnonymously().addOnCompleteListener {
                Log.d("DispatchActivity", "signInAnonymously:onComplete:" + it.isSuccessful)

                bibleApplication.currentUser = firebaseauth.currentUser

                //TODO: verify which bibles exists locally

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!it.isSuccessful) {
                    Log.w("DispatchActivity", "signInAnonymously", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loadBible()

        val intent = Intent(this, ReadActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadBible() {
        bibleApplication.currentBible = Bible.load(bibleApplication.preferences.selectedTranslation)

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
