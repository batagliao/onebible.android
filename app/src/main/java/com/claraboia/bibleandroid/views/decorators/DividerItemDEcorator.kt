package com.claraboia.bibleandroid.views.decorators

/**
 * Created by lucasbatagliao on 22/10/16.
 */
/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import com.claraboia.bibleandroid.R

class DividerItemDecoration(private val context: Context, orientation: Int) : RecyclerView.ItemDecoration() {
    //private val ATTRS: IntArray = intArrayOf(android.R.attr.listDivider)
    companion object {
        val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }

    private var mDivider: Drawable? = null
    private var mOrientation: Int = 0

    init {
        //val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        //mDivider = a.getDrawable(0)
        //a.recycle()

        mDivider = ContextCompat.getDrawable(context, R.drawable.linedivider)
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {

        val metrics = context.resources.displayMetrics
        val space =  (metrics.density * 12).toInt()

        val left = parent.paddingLeft + space
        val right = parent.width - parent.paddingRight - space

        val childCount = parent.childCount

        for (i in 0..childCount - 2) { //exclude last item
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider?.intrinsicHeight as Int
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }

    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0..childCount - 2) { //exclude last item
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider?.intrinsicHeight as Int
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider?.intrinsicHeight as Int)
        } else {
            outRect.set(0, 0, mDivider?.intrinsicWidth as Int, 0)
        }
    }
}