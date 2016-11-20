package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.WindowCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.animation.DecelerateInterpolator
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.CheatSheet
import com.claraboia.bibleandroid.helpers.asFullText
import com.claraboia.bibleandroid.helpers.getAddressText
import com.claraboia.bibleandroid.models.BibleAddress
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.app_bar_read.*
import kotlinx.android.synthetic.main.content_read.*


class ReadActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }
    private var isShowingBars = true
    private val UI_ANIMATION_DELAY = 300L
    private val mHideHandler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_read)


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setShowHideAnimationEnabled(true)

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        //menu definition
        btnOpenMenu.setOnClickListener { drawer.openDrawer(GravityCompat.START, true) }

        val openBooksIntent = Intent(this, SelectBooksActivity::class.java)
        //val openBooksIntent = Intent(this, TestFullscreenActivity::class.java)
        btnBooks.setOnClickListener { startActivity(openBooksIntent) }

        val openChapterIntent = Intent(this, SelectChapterActivity::class.java)
        readTitle.setOnClickListener { startActivity(openChapterIntent) }


        //set tooltips
        CheatSheet.setup(btnOpenMenu)
        CheatSheet.setup(btnBooks)
        CheatSheet.setup(btnTranslations)
        //CheatSheet.setup(btnShowBottomSheet)

        //bottomSheet
//        val bsBehavior = BottomSheetBehavior.from(readBottomSheet)
//        btnShowBottomSheet.setOnClickListener {
//
//
//            if(bsBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
//                bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                //appbarlayout.setExpanded(true, true)
//            }else if (bsBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
//                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                //appbarlayout.setExpanded(false, true)
//            }
//
//        }

        txtview.setOnClickListener {
            toggleVisibility()
        }

        loadText()

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        mHideHandler.removeCallbacks { hideSystemUI }
        mHideHandler.postDelayed(hideSystemUI, UI_ANIMATION_DELAY)
        //delayedHide(100)
    }


    override fun onBackPressed() {
        //val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.read, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private val hideSystemUI = Runnable {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        content_read.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or // hide nav bar //TODO: maybe remove this before
                        View.SYSTEM_UI_FLAG_FULLSCREEN or // hide status bar
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    private val showSystemUI = Runnable {
        showActionBar()
        //TODO: show bottom bar
    }

    private fun toggleVisibility() {
        if (isShowingBars)
            hide()
        else
            show()
    }

    private fun hideActionBar(){
        appbarlayout.animate()
                .translationY((- toolbar.height).toFloat())
                .setDuration(UI_ANIMATION_DELAY)
                .setInterpolator(DecelerateInterpolator())
    }

    private fun hide() {

        appbarlayout.setExpanded(false, true)

        //TODO: hide bottom bar

        isShowingBars = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(showSystemUI)
        mHideHandler.postDelayed(hideSystemUI, UI_ANIMATION_DELAY)


        hideActionBar()
    }

    private fun showActionBar(){
        appbarlayout.animate()
                .translationY(0F)
                .setDuration(UI_ANIMATION_DELAY)
                .setInterpolator(DecelerateInterpolator())
    }

    private fun show() {
        content_read.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        isShowingBars = true


        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(hideSystemUI)
        mHideHandler.postDelayed(showSystemUI, UI_ANIMATION_DELAY)
    }

    private fun loadText() {
        //convert actual position to address
        val address = BibleAddress()
        address.bookOrder = bibleApplication.currentBook
        address.chapterOrder = bibleApplication.currentChapter

        //set title
        readTitle.text = address.asFullText()

        //saves it as last address
        bibleApplication.preferences.lastAccessedAddress = address

        //loads corresponding text
        val text = bibleApplication.currentBible.getAddressText(address)

        //set text to view
        txtview.text = text


    }
}
