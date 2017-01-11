package com.claraboia.bibleandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.BibleTranslation

/**
 * Created by lucas.batagliao on 11/01/2017.
 */
class TranslationSelectRecyclerAdapter : RecyclerView.Adapter<TranslationSelectRecyclerAdapter.SelectTranslationViewHolder>() {

    // remove current Translation from list
    val bibles: List<BibleTranslation>
        get() {
            return BibleApplication.instance.localBibles.filter { b -> b.abbreviation != BibleApplication.instance.preferences.selectedTranslation.abbreviation }
        }


    inner class SelectTranslationViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(translation: BibleTranslation) {

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectTranslationViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.layout_select_translation_item, parent, false)
        val viewholder = SelectTranslationViewHolder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: SelectTranslationViewHolder?, position: Int) {
        holder?.bind(bibles[position])
    }

    override fun getItemCount(): Int {
        return bibles.size
    }
}