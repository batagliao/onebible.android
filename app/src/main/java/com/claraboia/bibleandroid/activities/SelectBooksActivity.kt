package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.TypedValue
import android.view.Menu
import android.widget.Toast
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.BookSelectionAdapter
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.views.BooksSelectDisplay
import com.claraboia.bibleandroid.views.BooksSelectSortType
import com.claraboia.bibleandroid.views.decorators.DividerItemDecoration
import com.claraboia.bibleandroid.views.decorators.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_select_books.*

class SelectBooksActivity : AppCompatActivity() {

    lateinit var bookAdapter: BookSelectionAdapter
    lateinit var gridItemDecoration : GridSpacingItemDecoration
    val dividerItemDecoration by lazy { DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST)}


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

        //grid item decoration
        val metrics = this.resources.displayMetrics
        val space =  (metrics.density * 12).toInt()
        gridItemDecoration = GridSpacingItemDecoration(2, space, true, 0)

        //click
        bookAdapter = BookSelectionAdapter(bibleApplication.booksForSelection, click = { b ->
            bibleApplication.currentBook = b.bookOrder
            val i = Intent(this, SelectChapterActivity::class.java)
            startActivity(i)
        })



        bookList.setHasFixedSize(true)
        bookList.itemAnimator = DefaultItemAnimator()
        bookList.adapter = bookAdapter
        setRecyclerView()

        groupSelectDisplayType.onChangeDisplayType += {
            setRecyclerView()
        }

        groupSelectSortType.onChangeSortType += {
            setRecyclerView()
        }

        groupSelectSortOrder.onChangeSortOrder += {
            setRecyclerView()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        //TODO: make search work

        // Associate searchable configuration with the SearchView
        // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        val searchView = MenuItemCompat.getActionView(menu?.findItem(R.id.action_search)) as SearchView
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setRecyclerView() {

        bookList.removeItemDecoration(dividerItemDecoration)
        bookList.removeItemDecoration(gridItemDecoration)

        if (groupSelectDisplayType.currentDisplayType == BooksSelectDisplay.BookLayoutDisplayType.GRID) {
            //GRID
            bookAdapter.displayType = BooksSelectDisplay.BookLayoutDisplayType.GRID
            bookList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            bookList.addItemDecoration(gridItemDecoration)
        } else {
            //LIST
            bookAdapter.displayType = BooksSelectDisplay.BookLayoutDisplayType.LIST
            bookList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            bookList.addItemDecoration(dividerItemDecoration)
        }

        if(groupSelectSortType.currentSortType == BooksSelectSortType.BookSortType.NORMAL){
            bookAdapter.sortNormal(groupSelectSortOrder.currentSortOrder)
        }else{
            bookAdapter.sortAlpha(groupSelectSortOrder.currentSortOrder)
        }

        //is it really needed to set adapter again everytime?
        bookList.adapter = bookAdapter
    }
}
