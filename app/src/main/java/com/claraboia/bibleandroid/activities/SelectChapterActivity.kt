package com.claraboia.bibleandroid.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.ChapterSelectionAdapter
import com.claraboia.bibleandroid.bibleApplication
import kotlinx.android.synthetic.main.activity_select_chapter.*

class SelectChapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_chapter)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(false)

        chapterList.setHasFixedSize(true)
        chapterList.itemAnimator = DefaultItemAnimator()

        //TODO: short this code, maybe an extension method
        val chapters = listOf(Any(), bibleApplication.currentBible.books[bibleApplication.currentBook -1].chapters)
        chapterList.adapter = ChapterSelectionAdapter(chapters)
        chapterList.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    }
}
