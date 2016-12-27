package com.claraboia.bibleandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.models.BibleTranslation

/**
 * Created by lucasbatagliao on 23/12/16.
 */
class TranslationLocalRecyclerAdapter(val translations: List<BibleTranslation>) : RecyclerView.Adapter<TranslationLocalRecyclerAdapter.TranslationViewHolder>() {
    inner class TranslationViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(translation: BibleTranslation){

        }
    }


    override fun getItemCount(): Int {
        return translations.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TranslationViewHolder {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TranslationViewHolder?, position: Int) {
        holder?.bind(translations[position])
    }
}
