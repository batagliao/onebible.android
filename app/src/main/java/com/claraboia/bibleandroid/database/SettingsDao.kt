package com.claraboia.bibleandroid.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.claraboia.bibleandroid.models.BibleAppSettings

@Dao
interface SettingsDao {
    @Query("SELECT * FROM BibleAppSettings LIMIT 1")
    fun getSettings(): BibleAppSettings

    @Update(onConflict = REPLACE)
    fun update(settings: BibleAppSettings)
}