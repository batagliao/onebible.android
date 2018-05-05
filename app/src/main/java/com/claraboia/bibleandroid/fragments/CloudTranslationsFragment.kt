package com.claraboia.bibleandroid.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.TranslationCloudRecyclerAdapter
import com.claraboia.bibleandroid.extensions.bibleApplication
import com.claraboia.bibleandroid.models.BibleTranslation
import com.claraboia.bibleandroid.services.*
import com.claraboia.bibleandroid.views.decorators.GridSpacingItemDecoration
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_cloud_translations.*
import kotlinx.android.synthetic.main.layout_translation_cloud_item.view.*
import java.util.*

//https://console.firebase.google.com/project/bible-2fb80/database/bible-2fb80/data
//stopped at 25

class CloudTranslationsFragment : Fragment() {

    private val database by lazy { FirebaseDatabase.getInstance().reference }
    var localTranslationFragment: LocalTranslationsFragment? = null

    private val translations: MutableList<BibleTranslation> = ArrayList()
    private val adapter = TranslationCloudRecyclerAdapter(translations,
            click = { t -> downloadTranslationClick(t) })

    private val downloadIntentReceiver = IntentReceiver()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cloud_translations, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        translationCloudList.visibility = View.GONE
        translationCloudList.layoutManager = LinearLayoutManager(activity)
        translationCloudList.adapter = adapter
        translationCloudList.setHasFixedSize(true)
        val metrics = this.resources.displayMetrics
        val space = (metrics.density * 12).toInt()

        translationCloudList.addItemDecoration(GridSpacingItemDecoration(1, space, true, 0))
        translationCloudList.addItemDecoration(DividerItemDecoration(this.context, VERTICAL))

        val query = database.child("translations").orderByChild("abbreviation")
        query.addListenerForSingleValueEvent(ListenerForDatabase())
    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(downloadIntentReceiver)
    }

    private fun downloadTranslationClick(translation: BibleTranslation) {
        if (activity!!.bibleApplication.localBibles.size == 0) {
            activity!!.bibleApplication.preferences.selectedTranslation = translation
        }

        val position = adapter.getTranslationPosition(translation.abbreviation)
        val viewholder = translationCloudList.findViewHolderForAdapterPosition(position)
        updateItem(viewholder as TranslationCloudRecyclerAdapter.TranslationViewHolder, 0)

        val svcintent = Intent(activity, DownloadTranslationService::class.java)
        svcintent.putExtra(EXTRA_TRANSLATION, translation)
        activity!!.startService(svcintent)
    }

    private fun registerReceiver() {
        val filter = IntentFilter(DOWNLOAD_TRANSLATION_PROGRESS_ACTION)
        LocalBroadcastManager.getInstance(activity!!)
                .registerReceiver(downloadIntentReceiver, filter)
    }

    inner class IntentReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //TODO: update adapter to mark the translation as downloading - show progress bar
            val translation = intent?.getStringExtra(DOWNLOAD_TRANSLATION_NAME_VALUE)
            val progress = intent?.getIntExtra(DOWNLOAD_TRANSLATION_PROGRESS_VALUE, 0)

            if (progress != null && progress > 99) {
                adapter.removeTranslation(translation)
            }

            if (progress != null) {
                val position = adapter.getTranslationPosition(translation)
                val viewholder = translationCloudList.findViewHolderForAdapterPosition(position)
                if(viewholder != null) {
                    updateItem(viewholder as TranslationCloudRecyclerAdapter.TranslationViewHolder, progress)
                }
            }

            Log.d("CLOUDTRANSLATION", "$translation >> $progress")
            //if there is only one translation > restart the app (first translation downloaded)
//            if(activity.bibleApplication.localBibles.size == 1){
//                activity.bibleApplication.doRestart()
//            }
        }
    }

    fun updateItem(vh: TranslationCloudRecyclerAdapter.TranslationViewHolder, progress: Int){
        //block download buytton if not
        //show progress bar if not
        //update progress bar value
        val view = vh.itemView
        if(view.item_translationDownload.visibility != View.INVISIBLE){
            view.item_translationDownload.visibility = View.INVISIBLE
        }

        if(view.item_translationDownloadProgressBar.visibility != View.VISIBLE){
            view.item_translationDownloadProgressBar.visibility = View.VISIBLE
        }

        if(view.item_translationDownloadProgressText.visibility != View.VISIBLE){
            view.item_translationDownloadProgressText.visibility = View.VISIBLE
        }


        view.item_translationDownloadProgressBar.progress = progress
        view.item_translationDownloadProgressText.text = progress.toString() + " %"

    }

    inner class ListenerForDatabase : ValueEventListener {
        override fun onCancelled(error: DatabaseError?) {
            Log.e("CloudTranslationsFragme", error.toString())
        }

        override fun onDataChange(snapshot: DataSnapshot?) {
            translations.clear()
            for (translationsnapshot in snapshot!!.children) {
                val translation = translationsnapshot.getValue(BibleTranslation::class.java)!!

                if (!activity!!.bibleApplication.localBibles.any { b -> b.abbreviation == translation.abbreviation }) {
                    //include only not downloaded translations
                    translations.add(translation)
                }
            }
            adapter.updateData(translations)
            translationCloudList.visibility = View.VISIBLE
            progress_bar.visibility = View.GONE
        }

    }

    //this method is here to LocalTranslationFragment be able to add removed translations back to this list
    fun addTranslation(translation: BibleTranslation) {
        adapter.addTranslation(translation)
    }

}