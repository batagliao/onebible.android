package com.claraboia.bibleandroid.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.helpers.CheatSheet
import kotlinx.android.synthetic.main.layout_books_selectsortorder.view.*

/**
 * Created by lucasbatagliao on 12/10/16.
 */
class BooksSelectSortOrder : RelativeLayout, View.OnClickListener {


    constructor(context: Context?) : super(context) {
        initLayout(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initLayout(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initLayout(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initLayout(context)
    }

    fun initLayout(context: Context?) {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_books_selectsortorder, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        //setup views
        CheatSheet.setup(btnSortOrderAsc)
        CheatSheet.setup(btnSortOrderDesc)

        btnSortOrderAsc.setOnClickListener(this)
        btnSortOrderDesc.setOnClickListener(this)

        //TODO: load state -> last btn pressed
        btnSortOrderAsc.isSelected = true
        btnSortOrderDesc.isSelected = false

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSortOrderAsc -> {
                if(!btnSortOrderAsc.isSelected) {
                    //TODO: set layout as grid
                    btnSortOrderAsc.isSelected = true
                    btnSortOrderDesc.isSelected = false
                    return
                }
                btnSortOrderAsc.isSelected = true
            }
            R.id.btnSortOrderDesc -> {
                if(!btnSortOrderDesc.isSelected) {
                    //TODO: set layout as list
                    btnSortOrderAsc.isSelected = false
                    btnSortOrderDesc.isSelected = true
                    return
                }
                btnSortOrderDesc.isSelected = true
            }
        }
    }
}