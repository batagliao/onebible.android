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
import kotlinx.android.synthetic.main.layout_books_grid_item.*
import kotlinx.android.synthetic.main.layout_books_grid_item.view.*

/**
 * Created by lucas.batagliao on 13/10/2016.
 */
class BookSelectionAdapter(val books: MutableList<Book>) : RecyclerView.Adapter<BookSelectionAdapter.BookSelectionViewHolder>() {

    class BookSelectionViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(book: Book) {
            val size = book.chapters.size
            itemView.item_ChapterQty.text = itemView.context.resources.getQuantityString(R.plurals.chapters, size, size)
            itemView.item_bookName.text =  book.getBookName()
            itemView.item_bookAbbrev.text = book.getBookAbbrev()
            itemView.item_book_frame.background =  book.getBookType().color()
        }



    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookSelectionViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.layout_books_grid_item, parent, false)
        val holder = BookSelectionViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: BookSelectionViewHolder?, position: Int) {
        val book = books[position]
        holder?.bind(book)
    }

}