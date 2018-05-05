package com.claraboia.bibleandroid.infrastructure

import android.content.Context.MODE_PRIVATE
import com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.BuildConfig


object FirstRun {
    private const val PREF_VERSION_CODE_KEY = "version_code"
    private const val DOESNT_EXISTS = -1

    const val FIRST_RUN = -1
    const val NORMAL = 0

    /**
     * @return
     * a) FirstRun.FIRST_RUN if its firt time the app is executed
     * b) FirstRun.NORMAL if the app is already ran in the same version its running now
     * c) The last version the application run before in case different that current version
     */
    fun checkFirstRun(): Int {

        // Get current version code
        val currentVersionCode = BuildConfig.VERSION_CODE

        // Get saved version code
        val prefs = getSharedPreferences(BibleApplication.instance.applicationContext)
        val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXISTS)

        // Check for first run or upgrade
        when {
            currentVersionCode == savedVersionCode -> // This is just a normal run
                return NORMAL
            savedVersionCode == DOESNT_EXISTS -> {
                prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
                return FIRST_RUN
            }
            currentVersionCode > savedVersionCode -> {
                // upgrade version
                prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
                return savedVersionCode
            }
            else -> {
                return NORMAL
            }
        }
    }

}

