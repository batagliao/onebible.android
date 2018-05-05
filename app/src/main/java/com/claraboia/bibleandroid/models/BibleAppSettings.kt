package com.claraboia.bibleandroid.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.claraboia.bibleandroid.views.BooksSelectDisplay
import com.claraboia.bibleandroid.views.BooksSelectSortOrder
import com.claraboia.bibleandroid.views.BooksSelectSortType

@Entity(tableName = "BibleAppSettings")
data class BibleAppSettings(
        @PrimaryKey(autoGenerate = true)
        var id: Long,

        @ColumnInfo
        var selectedTranslation: String = "",

        @ColumnInfo
        var bookSelectionDisplayType: BooksSelectDisplay.BookLayoutDisplayType,

        @ColumnInfo
        var bookSelectionSortType: BooksSelectSortType.BookSortType,

        @ColumnInfo
        var bookSelectionSortOrder: BooksSelectSortOrder.BookSortOrder,

        @ColumnInfo
        var lastAccessdBook: Int,

        @ColumnInfo
        var lastAccessdChapter: Int,

        @ColumnInfo
        var lastAccessedVerses: IntArray

) {

    constructor() :
            this(1, "kjv", BooksSelectDisplay.BookLayoutDisplayType.LIST,
                    BooksSelectSortType.BookSortType.NORMAL,
                    BooksSelectSortOrder.BookSortOrder.ASC,
                    1, 1,   intArrayOf(1)) {
    }
}