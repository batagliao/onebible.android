package com.claraboia.bibleandroid.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.fragments.CloudTranslationsFragment
import com.claraboia.bibleandroid.fragments.LocalTranslationsFragment

/**
 * Created by lucasbatagliao on 26/10/16.
 */
class TranslationsPagerAdapter(fm: FragmentManager?, private val context: Context) : FragmentPagerAdapter(fm) {

    private val PAGE_COUNT = 2
    private var titles: Array<String>
    private var fragments: Array<Fragment>


    init {
        titles = arrayOf(context.resources.getString(R.string.localTranslations),
                context.resources.getString(R.string.cloudTranslations))

        val localFragment = LocalTranslationsFragment()
        val cloudFragment = CloudTranslationsFragment()
        localFragment.cloudTranslationFragment = cloudFragment
        cloudFragment.localTranslationFragment = localFragment
        fragments = arrayOf(localFragment, cloudFragment)
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}