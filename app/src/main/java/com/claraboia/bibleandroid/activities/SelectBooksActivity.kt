package com.claraboia.bibleandroid.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.BookSelectionAdapter
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.CheatSheet
import com.claraboia.bibleandroid.views.BooksSelectDisplay
import com.claraboia.bibleandroid.views.BooksSelectSortOrder
import com.claraboia.bibleandroid.views.BooksSelectSortType
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

        groupSelectDisplayType.onChangeDisplayType += {
            when(it.displayType){
                BooksSelectDisplay.BookLayoutDisplayType.GRID -> TODO()
                BooksSelectDisplay.BookLayoutDisplayType.LIST -> TODO()
            }
        }

        groupSelectSortType.onChangeSortType += {
            when(it.sortType){
                BooksSelectSortType.BookSortType.NORMAL -> TODO()
                BooksSelectSortType.BookSortType.ALPHA -> TODO()
            }
        }

        groupSelectSortOrder.onChangeSortOrder += {
            when(it.sortOrder){
                BooksSelectSortOrder.BookSortOrder.ASC -> TODO()
                BooksSelectSortOrder.BookSortOrder.DESC -> TODO()
            }
        }

        //set recyclerview's things
        bookList.setHasFixedSize(true)
        setRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        // Associate searchable configuration with the SearchView
        // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        val searchView = MenuItemCompat.getActionView(menu?.findItem(R.id.action_search)) as SearchView
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    fun setRecyclerView(){
        bookList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        bookList.adapter = BookSelectionAdapter(bibleApplication.currentBible.books)
    }
}