package com.claraboia.bibleandroid.views

import android.content.Context
import android.support.v4.view.LayoutInflaterCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.helpers.CheatSheet
import com.claraboia.bibleandroid.infrastructure.Event
import com.claraboia.bibleandroid.infrastructure.EventArg
import kotlinx.android.synthetic.main.layout_books_selectdisplay.view.*

/**
 * Created by lucasbatagliao on 12/10/16.
 */
class BooksSelectDisplay : RelativeLayout, View.OnClickListener {

    enum class BookLayoutDisplayType{
        GRID,
        LIST
    }

    class ChangeDisplayTypeEventArgs(val displayType : BookLayoutDisplayType) : EventArg(){

    }

    val onChangeDisplayType : Event<ChangeDisplayTypeEventArgs> = Event()

    var currentDisplayType : BookLayoutDisplayType = BookLayoutDisplayType.GRID

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
        inflater.inflate(R.layout.layout_books_selectdisplay, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        //setup views
        CheatSheet.setup(btnViewAsGrid)
        CheatSheet.setup(btnViewAsList)

        btnViewAsGrid.setOnClickListener(this)
        btnViewAsList.setOnClickListener(this)

        //TODO: load state -> last btn pressed
        btnViewAsGrid.isSelected = true
        btnViewAsList.isSelected = false

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnViewAsGrid -> {
                if(!btnViewAsGrid.isSelected) {
                    currentDisplayType = BookLayoutDisplayType.GRID
                    onChangeDisplayType.invoke(ChangeDisplayTypeEventArgs(currentDisplayType))
                    btnViewAsGrid.isSelected = true
                    btnViewAsList.isSelected = false
                    return
                }
                btnViewAsGrid.isSelected = true
            }
            R.id.btnViewAsList -> {
                if(!btnViewAsList.isSelected) {
                    currentDisplayType = BookLayoutDisplayType.LIST
                    onChangeDisplayType.invoke(ChangeDisplayTypeEventArgs(currentDisplayType))
                    btnViewAsGrid.isSelected = false
                    btnViewAsList.isSelected = true
                    return
                }
                btnViewAsList.isSelected = true
            }
        }
    }
}