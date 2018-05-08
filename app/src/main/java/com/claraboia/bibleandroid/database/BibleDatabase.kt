package com.claraboia.bibleandroid.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.claraboia.bibleandroid.models.BibleTranslation

@Database(entities = arrayOf(BibleTranslation::class), version = 1)
abstract class  BibleDatabase: RoomDatabase() {

   abstract fun translationsDao(): TranslationsDao

    companion object {
        private var INSTANCE: BibleDatabase? = null

        fun getInstance(context: Context): BibleDatabase? {
            if (INSTANCE == null) {
                synchronized(BibleDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            BibleDatabase::class.java, "bibles-metadata.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}