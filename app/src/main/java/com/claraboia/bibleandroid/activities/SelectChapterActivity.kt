package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.ChapterSelectionAdapter
import com.claraboia.bibleandroid.extensions.bibleApplication
import com.claraboia.bibleandroid.repositories.BibleTextRepository
import com.claraboia.bibleandroid.views.decorators.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_select_chapter.*

class SelectChapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_chapter)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        chapterList.setHasFixedSize(true)
        chapterList.itemAnimator = DefaultItemAnimator()

        //TODO: short this code, maybe an extension method
        val book = bibleApplication.currentBible.books[bibleApplication.currentBook -1]

        supportActionBar?.title = BibleTextRepository.getBookName(book)

        val chapters = listOf(Any()) + book.chapters
        chapterList.adapter = ChapterSelectionAdapter(book, chapters, chapterClick = { c ->
            bibleApplication.currentChapter = c.chapterOrder
            val i = Intent(this, ReadActivity::class.java)
            startActivity(i)
        })

        val gridlayout = GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)
        gridlayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if(position == 0){
                    return gridlayout.spanCount
                }else{
                    return 1
                }
            }
        }
        chapterList.layoutManager = gridlayout
        val metrics = this.resources.displayMetrics
        val space =  (metrics.density * 12).toInt() // resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        chapterList.addItemDecoration(GridSpacingItemDecoration(5, space, true, 1))
    }

}


