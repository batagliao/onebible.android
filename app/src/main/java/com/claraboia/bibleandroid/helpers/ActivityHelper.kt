package com.claraboia.bibleandroid.helpers

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.claraboia.bibleandroid.R
import com.github.chenglei1986.statusbar.StatusBarColorManager

fun AppCompatActivity.setStatusIconsColor(fullscreen: Boolean){
    val statusBarColorManager = StatusBarColorManager(this)
    val layoutFullscreen = fullscreen // or false

    var withActionBar = false
//    if(this.supportActionBar != null) {
//        withActionBar = true // or true
//    }

    statusBarColorManager.setStatusBarColorRes(R.color.colorPrimary, layoutFullscreen, withActionBar)
    //statusBarColorManager.setStatusBarColorRes(R.color.status_bar_color, layoutFullscreen, withActionBar);
    //statusBarColorManager.setStatusBarBackground(drawable, layoutFullscreen, withActionBar);
    //statusBarColorManager.setStatusBarBackground(R.drawable.statu_bar_background, layoutFullscreen, withActionBar);
    statusBarColorManager.setLightStatusBar(true)
}