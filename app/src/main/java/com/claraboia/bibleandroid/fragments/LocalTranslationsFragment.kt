package com.claraboia.bibleandroid.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claraboia.bibleandroid.R;
import com.claraboia.bibleandroid.adapters.TranslationCloudRecyclerAdapter
import com.claraboia.bibleandroid.adapters.TranslationLocalRecyclerAdapter
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.views.decorators.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_local_translations.*

/**
 * A simple {@link Fragment} subclass.
 */
class LocalTranslationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_local_translations, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        translationLocalList.layoutManager = LinearLayoutManager(activity)
        val translations = activity.bibleApplication.localBibles
        val adapter = TranslationLocalRecyclerAdapter(translations)
        translationLocalList.adapter = adapter
        val metrics = this.resources.displayMetrics
        val space =  (metrics.density * 12).toInt()
        translationLocalList.addItemDecoration(GridSpacingItemDecoration(1, space, true, 0))
    }

}
