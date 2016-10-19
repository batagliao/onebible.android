package com.claraboia.bibleandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.Book
import com.claraboia.bibleandroid.models.Chapter
import kotlinx.android.synthetic.main.layout_chapter_item.view.*

/**
 * Created by lucas.batagliao on 18/10/2016.
 */
class ChapterSelectionAdapter(val book: Book, val chapters: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ITEM: Int = 1
    private val TYPE_SUMMARY: Int = 2

    // view holders
    class ChapterItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun  bind(chapter: Chapter) {
            itemView.item_chapter_order.text = chapter.chapterOrder.toString()
        }

    }

    class ChapterSummaryViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }


    override fun getItemViewType(position: Int): Int {
        if (position == 0) return TYPE_SUMMARY

        return TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder?.itemViewType == TYPE_ITEM){
            (holder as ChapterItemViewHolder).bind(chapters[position] as Chapter)
        }else{
            (holder as ChapterSummaryViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)

        val view: View
        val holder: RecyclerView.ViewHolder
        if(viewType == TYPE_ITEM){
            view = inflater.inflate(R.layout.layout_chapter_item, parent, false)
            holder = ChapterItemViewHolder(view)
        }else{
            view = inflater.inflate(R.layout.layout_chapter_summary, parent, false)
            holder = ChapterSummaryViewHolder(view)
        }
        return holder
    }
}