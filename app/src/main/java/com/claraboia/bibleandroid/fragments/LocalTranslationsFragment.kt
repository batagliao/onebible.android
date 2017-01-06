package com.claraboia.bibleandroid.fragments;


import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle;
import android.support.annotation.Nullable
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claraboia.bibleandroid.R;
import com.claraboia.bibleandroid.adapters.TranslationCloudRecyclerAdapter
import com.claraboia.bibleandroid.adapters.TranslationLocalRecyclerAdapter
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.getFileName
import com.claraboia.bibleandroid.helpers.removeFromLocalTranslations
import com.claraboia.bibleandroid.helpers.saveLocalTranslations
import com.claraboia.bibleandroid.models.BibleTranslation
import com.claraboia.bibleandroid.services.DOWNLOAD_TRANSLATION_NAME_VALUE
import com.claraboia.bibleandroid.services.DOWNLOAD_TRANSLATION_PROGRESS_VALUE
import com.claraboia.bibleandroid.views.decorators.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_local_translations.*
import java.io.File

/**
 * A simple {@link Fragment} subclass.
 */
class LocalTranslationsFragment : Fragment() {

    private val adapter = TranslationLocalRecyclerAdapter(click = { t -> deleteTranslationClick(t) })
    var cloudTranslationFragment: CloudTranslationsFragment? = null

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_local_translations, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        translationLocalList.layoutManager = LinearLayoutManager(activity)
        translationLocalList.adapter = adapter
        val metrics = this.resources.displayMetrics
        val space = (metrics.density * 12).toInt()
        translationLocalList.addItemDecoration(GridSpacingItemDecoration(1, space, true, 0))
    }

    private fun deleteTranslationClick(translation: BibleTranslation) {

        //check if is the selected translation
        if (translation.abbreviation == activity.bibleApplication.preferences.selectedTranslation.abbreviation) {
            AlertDialog.Builder(activity)
                    .setTitle(R.string.delete)
                    .setMessage(R.string.delete_unable_to_delete_active_translation)
                    .setPositiveButton(R.string.ok, null)
                    .show()
        } else {

            val message = resources.getString(R.string.delete_confirmation_message).format("${translation.abbreviation} - ${translation.name}")
            AlertDialog.Builder(activity)
                    .setTitle(R.string.delete)
                    .setMessage(message)
                    .setNegativeButton(R.string.No, null)
                    .setPositiveButton(R.string.Yes, { dialogInterface: DialogInterface, i: Int -> performDeleteTranslation(translation) })
                    .show()
        }
    }

    private fun performDeleteTranslation(translation: BibleTranslation) {
        val file = File(translation.getFileName())
        file.delete()
        translation.removeFromLocalTranslations()
        //add it to cloudTranslationsBack
        cloudTranslationFragment?.addTranslation(translation)
    }


    inner class intentReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //TODO: update adapter to mark the translation as downloading - show progress bar
            val translation = intent?.getStringExtra(DOWNLOAD_TRANSLATION_NAME_VALUE)
            val progress = intent?.getIntExtra(DOWNLOAD_TRANSLATION_PROGRESS_VALUE, 0)

            if (progress != null && progress > 99) {
                adapter.notifyChanged()
            }
        }
    }

}
