package com.claraboia.bibleandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.helpers.CheatSheet
import com.claraboia.bibleandroid.models.BibleTranslation
import kotlinx.android.synthetic.main.layout_translation_cloud_item.view.*

/**
 * Created by lucasbatagliao on 28/10/16.
 */
class TranslationCloudRecyclerAdapter(var translations: MutableList<BibleTranslation>,
                                      val click: (translation: BibleTranslation) -> Unit) : RecyclerView.Adapter<TranslationCloudRecyclerAdapter.TranslationViewHolder>() {

    inner class TranslationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(translation: BibleTranslation){
            itemView.item_translationName.text = translation.name
            itemView.item_translationAbbrev.text = translation.abbreviation
            itemView.item_translationSize.text = translation.fileSize.toString() + "MB"
            itemView.item_translationDownload.setOnClickListener {
                click.invoke(translation)
            }
            itemView.item_translationDownloadProgressBar.visibility = View.INVISIBLE
            itemView.item_translationDownloadProgressText.visibility = View.INVISIBLE
            CheatSheet.setup(itemView.item_translationDownload)
        }
    }

    fun getTranslationPosition(abbreviation: String?): Int{
        return translations.indexOfFirst { t -> t.abbreviation.toLowerCase() == abbreviation?.toLowerCase() }
    }

    fun updateData(translations: MutableList<BibleTranslation>){
        this.translations = translations
        notifyDataSetChanged()
    }

    fun addTranslation(translation: BibleTranslation) {
        translations.add(translation)
        translations.sortBy { t -> t.abbreviation }
        notifyDataSetChanged()
    }

    fun removeTranslation(abbreviation: String?) {
        val i  = translations.indexOfFirst { t -> t.abbreviation.toLowerCase() == abbreviation?.toLowerCase() }
        if(i > -1) {
            translations.removeAt(i)
            notifyItemRemoved(i)
            notifyItemRangeChanged(i, translations.count())
        }
    }

    override fun getItemCount(): Int {
        return translations.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TranslationViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.layout_translation_cloud_item, parent, false)
        return TranslationViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationViewHolder?, position: Int) {
        holder?.bind(translations[position])
    }




}

