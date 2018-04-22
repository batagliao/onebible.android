package com.claraboia.bibleandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.helpers.CheatSheet
import com.claraboia.bibleandroid.models.BibleTranslation
import kotlinx.android.synthetic.main.layout_translation_local_item.view.*

/**
 * Created by lucasbatagliao on 23/12/16.
 */
class TranslationLocalRecyclerAdapter(val click: (translation: BibleTranslation) -> Unit) : RecyclerView.Adapter<TranslationLocalRecyclerAdapter.TranslationViewHolder>() {

    inner class TranslationViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(translation: BibleTranslation){
            itemView.item_translationName.text = translation.name
            itemView.item_translationAbbrev.text = translation.abbreviation
            itemView.item_translationSize.text = translation.fileSize.toString() + "MB"
            itemView.item_translationDelete.setOnClickListener {
                click.invoke(translation)
            }
            CheatSheet.setup(itemView.item_translationDelete)
        }
    }

    fun notifyChanged() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return BibleApplication.instance.localBibles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_translation_local_item, parent, false)
        return TranslationViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationViewHolder, position: Int) {
        holder.bind(BibleApplication.instance.localBibles[position])
    }


}
