package com.claraboia.bibleandroid.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.helpers.CheatSheet
import kotlinx.android.synthetic.main.activity_select_books.*

class SelectBooksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_books)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(false)

        // Calculate ActionBar height
        val tv = TypedValue()
        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            val actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
            val params = barBellowToolbar.layoutParams
            params.height = actionBarHeight * 2 - 44
            barBellowToolbar.layoutParams = params
        }

        //set tooltips
        CheatSheet.setup(btnViewAsGrid)
        CheatSheet.setup(btnViewAsList)
        CheatSheet.setup(btnSortAlpha)
        CheatSheet.setup(btnSortNormal)
        CheatSheet.setup(btnSortOrder)
        

    }

}
