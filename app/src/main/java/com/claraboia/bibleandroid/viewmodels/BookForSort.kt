package com.claraboia.bibleandroid.viewmodels

import com.claraboia.bibleandroid.models.BookTypeEnum

/**
 * Created by lucasbatagliao on 16/10/16.
 */

/**
 *  This class is used for SelectionBookActivity be able to sort the books without the need to
 *  change the original list
 */
data class BookForSort(val bookOrder : Int, val chapterCount : Int, val bookName : String,
                       val bookAbbrev : String, val type : BookTypeEnum) {
}