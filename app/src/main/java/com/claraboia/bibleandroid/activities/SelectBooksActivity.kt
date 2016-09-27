package com.claraboia.bibleandroid.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.claraboia.bibleandroid.R
import kotlinx.android.synthetic.main.activity_select_books.*

class SelectBooksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_books)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        //supportActionBar?.setDisplayShowCustomEnabled(false)
    }
}
