package com.claraboia.bibleandroid.helpers

import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.BuildConfig

/**
 * Created by lucas.batagliao on 13/10/2016.
 */
class ResourceHelper {

    companion object {

        fun getStringByName(resourceName: String): String {
            val resource = BibleApplication.instance.resources
            val id = resource.getIdentifier(resourceName, "string", BuildConfig.APPLICATION_ID)
            return resource.getString(id)
        }
    }
}