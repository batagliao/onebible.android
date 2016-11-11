package com.claraboia.bibleandroid.activities

import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.claraboia.bibleandroid.BibleApplication
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

    //val txtView = this.findViewById(R.id.txtview) as TextView
    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        //menu definition
        btnOpenMenu.setOnClickListener { drawer.openDrawer(GravityCompat.START, true) }

        val openBooksIntent = Intent(this, SelectBooksActivity::class.java)
        btnBooks.setOnClickListener { startActivity(openBooksIntent) }

        val openChapterIntent = Intent(this, SelectChapterActivity::class.java)
        readTitle.setOnClickListener { startActivity(openChapterIntent) }

        //set tooltips
        CheatSheet.setup(btnOpenMenu)
        CheatSheet.setup(btnBooks)
        CheatSheet.setup(btnTranslations)
        CheatSheet.setup(btnShowBottomSheet)

        //bottomSheet
        val bsBehavior = BottomSheetBehavior.from(readBottomSheet)
        btnShowBottomSheet.setOnClickListener {
            if(bsBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else if (bsBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }


        loadText()

    }

    private fun loadText(){
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
}
