package com.claraboia.bibleandroid.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.claraboia.bibleandroid.models.BibleAppSettings
import com.claraboia.bibleandroid.repositories.SettingsRepository

@Database(entities = arrayOf(BibleAppSettings::class), version = 1)
abstract class  BibleDatabase: RoomDatabase() {

    abstract fun BibleSettingsRepository(): SettingsRepository

    companion object {
        private var INSTANCE: BibleDatabase? = null

        fun getInstance(context: Context): BibleDatabase? {
            if (INSTANCE == null) {
                synchronized(BibleDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BibleDatabase::class.java, "weather.db")
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