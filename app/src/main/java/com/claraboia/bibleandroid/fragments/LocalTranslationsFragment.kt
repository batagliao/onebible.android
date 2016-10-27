package com.claraboia.bibleandroid.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claraboia.bibleandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
class LocalTranslationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_local_translations, container, false)
    }

}
