package com.claraboia.bibleandroid.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.CheatSheet
import com.claraboia.bibleandroid.infrastructure.Event
import com.claraboia.bibleandroid.infrastructure.EventArg
import kotlinx.android.synthetic.main.layout_books_selectsortorder.view.*

/**
 * Created by lucasbatagliao on 12/10/16.
 */
class BooksSelectSortOrder : RelativeLayout, View.OnClickListener {

    enum class BookSortOrder {
        ASC,
        DESC
    }

    class ChangeSortOrderEventArgs(val sortOrder: BookSortOrder) : EventArg() {

    }

    val onChangeSortOrder: Event<ChangeSortOrderEventArgs> = Event()

    var currentSortOrder: BookSortOrder = BookSortOrder.ASC

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
        currentSortOrder = context.bibleApplication.preferences.bookSelectionSortOrder
        setButtons()

    }

    private fun setButtons() {
        if (currentSortOrder == BookSortOrder.ASC) {
            btnSortOrderAsc.isSelected = true
            btnSortOrderDesc.isSelected = false
        } else {
            btnSortOrderAsc.isSelected = false
            btnSortOrderDesc.isSelected = true
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val parc = super.onSaveInstanceState()
        val ss = SavedState(parc)
        ss.sortOrder = currentSortOrder
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val state = state as SavedState
        super.onRestoreInstanceState(state)
        currentSortOrder = state.sortOrder
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSortOrderAsc -> {
                if (!btnSortOrderAsc.isSelected) {
                    currentSortOrder = BookSortOrder.ASC
                    onChangeSortOrder.invoke(ChangeSortOrderEventArgs(currentSortOrder))
                }
            }
            R.id.btnSortOrderDesc -> {
                if (!btnSortOrderDesc.isSelected) {
                    currentSortOrder = BookSortOrder.DESC
                    onChangeSortOrder.invoke(ChangeSortOrderEventArgs(currentSortOrder))
                }
            }
        }
        context.bibleApplication.preferences.bookSelectionSortOrder = currentSortOrder
        setButtons()
    }

    private class SavedState(source: Parcelable) : BaseSavedState(source) {

        var sortOrder = BookSortOrder.ASC

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(sortOrder.name)
        }
    }
}