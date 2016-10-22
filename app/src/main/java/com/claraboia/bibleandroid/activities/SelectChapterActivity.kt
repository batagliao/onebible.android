package com.claraboia.bibleandroid.activities

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.ChapterSelectionAdapter
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.getBookName
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

        supportActionBar?.title = book.getBookName()

        val chapters = listOf(Any()) + book.chapters
        chapterList.adapter = ChapterSelectionAdapter(book, chapters)
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

    /**
     * Created by Android Studio
     * User: Ailurus(ailurus@foxmail.com)
     * Date: 2015-10-28
     * Time: 15:20
     */
     inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean, private val headerNum: Int): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State ) {
            val position = parent.getChildAdapterPosition(view) - headerNum // item position

            if (position >= 0) {
                val column = position % spanCount // item column

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing // item top
                    }
                }
            } else {
                outRect.left = 0
                outRect.right = 0
                outRect.top = 0
                outRect.bottom = 0
            }
        }
    }
}


