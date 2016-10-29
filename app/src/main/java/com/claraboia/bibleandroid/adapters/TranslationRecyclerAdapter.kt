package com.claraboia.bibleandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.models.BibleTranslation
import kotlinx.android.synthetic.main.layout_translation_item.view.*

/**
 * Created by lucasbatagliao on 28/10/16.
 */
class TranslationRecyclerAdapter(var translations: List<BibleTranslation>) : RecyclerView.Adapter<TranslationRecyclerAdapter.TranslationViewHolder>() {

    inner class TranslationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(translation: BibleTranslation){
            itemView.item_translationName.text = translation.name
        }
    }

    fun updateData(translations: List<BibleTranslation>){
        this.translations = translations
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return translations.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TranslationViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.layout_translation_item, parent, false)
        return TranslationViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationViewHolder?, position: Int) {
        holder?.bind(translations[position])
    }
}

