package com.claraboia.bibleandroid.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.fragments.ReadFragment
import com.claraboia.bibleandroid.helpers.asFullText
import com.claraboia.bibleandroid.helpers.next
import com.claraboia.bibleandroid.helpers.previous
import com.claraboia.bibleandroid.models.BibleAddress

/**
 * Created by lucasbatagliao on 03/12/16.
 */
// FragmnetPagerAdapter does not destoy fragments, only detaches

const val CENTER = Int.MAX_VALUE / 2


class ReadViewPagerAdapter(val viewpager: ViewPager, val fm: FragmentManager?) : FragmentPagerAdapter(fm), ViewPager.OnPageChangeListener {

    private var showingPosition = -1
    private var showingFragment: ReadFragment? = null
    private var onPageChangeListener: ((BibleAddress) -> Unit)? = null


    override fun onPageScrollStateChanged(state: Int) { }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {  }

    override fun onPageSelected(position: Int) {
        showingPosition = position
        showingFragment = getVisibleFragment(position)
        val address = showingFragment?.address

        address?.let { BibleApplication.instance.updatePosition(it) }

        if(onPageChangeListener != null && address != null){
            onPageChangeListener?.invoke(address)
        }
    }

    init {
        viewpager.addOnPageChangeListener(this)
    }

    private fun getVisibleFragment(position: Int): ReadFragment? {
        val tag = "android:switcher:${viewpager.id}:$position"
        val currFrag = fm?.findFragmentByTag(tag)
        return currFrag as ReadFragment?
    }


    fun setPageChangedListener(onchange: (address:BibleAddress) -> Unit){
        this.onPageChangeListener = onchange
    }

    //TODO: implement destroy item to get rid of the old fragments???
    //if so, getItemPosition needs to return POSITION_NONE to force adapter to create fragmente always

    override fun getItem(position: Int): Fragment {
        var address = BibleApplication.instance.currentAddress
        if(showingFragment != null){
            address = showingFragment?.address as BibleAddress
        }

        if (showingPosition > -1 && position > showingPosition) {
            address = address.next()
        } else if (showingPosition > -1 && position < showingPosition) {
            address = address.previous()
        }


        val item = ReadFragment.newInstance(address)

        //this works fot the first time only
        if(showingFragment == null) {
            showingFragment = item
        }
        //this works fot the first time only
        if(showingPosition == -1){
            showingPosition = position
        }

        Log.d("ADAPTER", "getItem position $position >> value >> ${item.address.asFullText()}")
        return item
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }





}