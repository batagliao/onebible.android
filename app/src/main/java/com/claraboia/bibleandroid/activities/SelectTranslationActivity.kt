package com.claraboia.bibleandroid.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.TranslationsPagerAdapter
import kotlinx.android.synthetic.main.activity_select_translation.*

const val SHOULD_OPEN_CLOUD_TAB_KEY = "OpenToCloudTab"

class SelectTranslationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_translation)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        translationViewPager.adapter = TranslationsPagerAdapter(supportFragmentManager, this)
        translationTabLayout.setupWithViewPager(translationViewPager)

        val openOnCloudTab = intent.getBooleanExtra(SHOULD_OPEN_CLOUD_TAB_KEY, false)
        if(openOnCloudTab){
            translationViewPager.setCurrentItem(1, false)
        }

    }


}
