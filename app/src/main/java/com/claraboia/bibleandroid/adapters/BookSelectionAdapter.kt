package com.claraboia.bibleandroid.adapters

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.helpers.getBookAbbrev
import com.claraboia.bibleandroid.helpers.getBookName
import com.claraboia.bibleandroid.helpers.getBookType
import com.claraboia.bibleandroid.models.Book
import com.claraboia.bibleandroid.models.BookTypeEnum
import com.claraboia.bibleandroid.viewmodels.BookForSort
import com.claraboia.bibleandroid.views.BooksSelectDisplay
import com.claraboia.bibleandroid.views.BooksSelectSortOrder
import kotlinx.android.synthetic.main.layout_books_grid_item.*
import kotlinx.android.synthetic.main.layout_books_grid_item.view.*
import java.util.*
import java.util.Collections.sort
import kotlin.comparisons.compareBy
import kotlin.comparisons.compareByDescending

/**
 * Created by lucas.batagliao on 13/10/2016.
 */
class BookSelectionAdapter(val books: MutableList<BookForSort>, val click: (item: BookForSort) -> Unit) : RecyclerView.Adapter<BookSelectionAdapter.BookSelectionViewHolder>() {
    private val booksCopy: List<BookForSort> = ArrayList(books)


    class BookSelectionViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(book: BookForSort, click: (BookForSort) -> Unit) {
            val size = book.chapterCount
            itemView.item_ChapterQty.text = itemView.context.resources.getQuantityString(R.plurals.chapters, size, size)
            itemView.item_bookName.text =  book.bookName
            itemView.item_bookAbbrev.text = book.bookAbbrev
            itemView.item_book_frame.background =  book.type.color()
            itemView.item_book_card.setOnClickListener { click.invoke(book) }
        }
    }

    var displayType : BooksSelectDisplay.BookLayoutDisplayType = BooksSelectDisplay.BookLayoutDisplayType.GRID


    override fun getItemCount(): Int {
        return books.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookSelectionViewHolder {
        val inflater = LayoutInflater.from(parent?.context)

        val view: View
        if(displayType == BooksSelectDisplay.BookLayoutDisplayType.GRID) {
            view = inflater.inflate(R.layout.layout_books_grid_item, parent, false)
        }else{
            view = inflater.inflate(R.layout.layout_books_list_item, parent, false)
        }
        val holder = BookSelectionViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: BookSelectionViewHolder?, position: Int) {
        val book = books[position]
        holder?.bind(book, click)
    }

    fun sortNormal(order: BooksSelectSortOrder.BookSortOrder){
        if(order == BooksSelectSortOrder.BookSortOrder.ASC) {
            books.sortWith(compareBy { it.bookOrder })
        }else{
            books.sortWith(compareByDescending { it.bookOrder })
        }
        notifyItemRangeChanged(0, itemCount -1)
    }

    fun sortAlpha(order: BooksSelectSortOrder.BookSortOrder){
        if(order == BooksSelectSortOrder.BookSortOrder.ASC){
            books.sortWith(compareBy { it.bookName })
        }else{
            books.sortWith(compareByDescending { it.bookName })
        }
        notifyItemRangeChanged(0, itemCount -1)
    }

    fun filter(query: String?){
        books.clear()
        if(query.isNullOrEmpty()){
            books.addAll(booksCopy)
        }else{
            val text = query!!.toLowerCase()
            for(b in booksCopy){
                if(b.bookName.toLowerCase().contains(text)
                || b.bookAbbrev.toLowerCase().contains(text)){
                    books.add(b)
                }
            }
        }
        notifyDataSetChanged()
    }

//    fun notifyRemoveEach() {
//        for (i in 0..books.size - 1) {
//            notifyItemRemoved(i)
//        }
//
//    }
//
//    fun notifyAddEach() {
//        for (i in 0..books.size - 1) {
//            notifyItemInserted(i)
//        }
//    }

}