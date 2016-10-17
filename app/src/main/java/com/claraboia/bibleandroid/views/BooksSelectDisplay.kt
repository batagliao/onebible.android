package com.claraboia.bibleandroid.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.view.LayoutInflaterCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.bibleApplication
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

        currentDisplayType = context.bibleApplication.preferences.bookSelectionDisplayType
        setButtons()

    }

    private fun setButtons(){
        if(currentDisplayType == BookLayoutDisplayType.GRID){
            btnViewAsGrid.isSelected = true
            btnViewAsList.isSelected = false
        }else{
            btnViewAsGrid.isSelected = false
            btnViewAsList.isSelected = true
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val parc = super.onSaveInstanceState()
        val ss = SavedState(parc)
        ss.displayType = currentDisplayType
        return ss
    }

    override fun onRestoreInstanceState(parc: Parcelable?) {
        val state = parc as SavedState
        super.onRestoreInstanceState(state)
        currentDisplayType = state.displayType
        setButtons()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnViewAsGrid -> {
                if(!btnViewAsGrid.isSelected) {
                    currentDisplayType = BookLayoutDisplayType.GRID
                    onChangeDisplayType.invoke(ChangeDisplayTypeEventArgs(currentDisplayType))
                }
            }
            R.id.btnViewAsList -> {
                if(!btnViewAsList.isSelected) {
                    currentDisplayType = BookLayoutDisplayType.LIST
                    onChangeDisplayType.invoke(ChangeDisplayTypeEventArgs(currentDisplayType))
                }
            }
        }
        context.bibleApplication.preferences.bookSelectionDisplayType = currentDisplayType
        setButtons()
    }

    private class SavedState(source: Parcelable) : BaseSavedState(source) {

        var displayType = BookLayoutDisplayType.GRID

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(displayType.name)
        }
    }
}