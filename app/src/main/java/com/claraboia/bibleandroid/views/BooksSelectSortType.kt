package com.claraboia.bibleandroid.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.extensions.bibleApplication
import com.claraboia.bibleandroid.helpers.CheatSheet
import com.claraboia.bibleandroid.infrastructure.Event
import com.claraboia.bibleandroid.infrastructure.EventArg
import kotlinx.android.synthetic.main.layout_books_selectsorttype.view.*

class BooksSelectSortType : RelativeLayout, View.OnClickListener {

    enum class BookSortType {
        NORMAL,
        ALPHA
    }

    class ChangeSortTypeEventArgs(val sortType: BookSortType) : EventArg()

    val onChangeSortType: Event<ChangeSortTypeEventArgs> = Event()

    var currentSortType: BookSortType = BookSortType.NORMAL

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
        inflater.inflate(R.layout.layout_books_selectsorttype, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        //setup views
        CheatSheet.setup(btnSortNormal)
        CheatSheet.setup(btnSortAlpha)

        btnSortNormal.setOnClickListener(this)
        btnSortAlpha.setOnClickListener(this)

        currentSortType = context.bibleApplication.preferences.bookSelectionSortType
        setButtons()
    }

    private fun setButtons() {
        if (currentSortType == BookSortType.NORMAL) {
            btnSortNormal.isSelected = true
            btnSortAlpha.isSelected = false
        } else {
            btnSortNormal.isSelected = false
            btnSortAlpha.isSelected = true
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val parc = super.onSaveInstanceState()
        val ss = SavedState(parc)
        ss.sortType = currentSortType
        return ss
    }

    override fun onRestoreInstanceState(parc: Parcelable?) {
        val state = parc as SavedState
        super.onRestoreInstanceState(state)
        currentSortType = state.sortType
        setButtons()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSortNormal -> {
                if (!btnSortNormal.isSelected) {
                    currentSortType = BookSortType.NORMAL
                    onChangeSortType.invoke(ChangeSortTypeEventArgs(currentSortType))
                }
            }
            R.id.btnSortAlpha -> {
                if (!btnSortAlpha.isSelected) {
                    currentSortType = BookSortType.ALPHA
                    onChangeSortType.invoke(ChangeSortTypeEventArgs(currentSortType))
                }
            }
        }
        context.bibleApplication.preferences.bookSelectionSortType = currentSortType
        setButtons()
    }

    private class SavedState(source: Parcelable) : BaseSavedState(source) {

        var sortType = BookSortType.NORMAL

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(sortType.name)
        }
    }
}