package com.claraboia.bibleandroid.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.claraboia.bibleandroid.models.BibleTranslation

@Dao()
interface TranslationsDao{

    @Query("select * from translation")
    fun getAll() : List<BibleTranslation>

    @Insert(onConflict = REPLACE)
    fun insert(translation: BibleTranslation)

    @Update(onConflict = REPLACE)
    fun update(translation: BibleTranslation)

    @Delete()
    fun delete(translation: BibleTranslation)

}