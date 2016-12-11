package com.claraboia.bibleandroid.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.ViewGroup
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.fragments.ReadFragment
import com.claraboia.bibleandroid.helpers.asFullText
import com.claraboia.bibleandroid.helpers.next
import com.claraboia.bibleandroid.helpers.previous
import com.claraboia.bibleandroid.models.BibleAddress
import com.claraboia.bibleandroid.views.ReadViewPager

/**
 * Created by lucasbatagliao on 03/12/16.
 */
// FragmnetPagerAdapter does not destoy fragments, only detaches

class ReadViewPagerAdapter(val viewpager: ViewPager, val fm: FragmentManager?) : FragmentPagerAdapter(fm), ViewPager.OnPageChangeListener {
    //private val fragments = kotlin.arrayOfNulls<Fragment>(3)
    private var showingPosition = -1
    private var showingFragment: ReadFragment? = null


    override fun onPageScrollStateChanged(state: Int) {
        Log.d("ADAPTER", "Scroll sate > $state")
        if (state == ViewPager.SCROLL_STATE_IDLE && showingPosition != viewpager.currentItem) {
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d("ADAPTER", "PageScrolled position = $position    ofsset = $positionOffset")
        if (positionOffset == 0f) {
            //scroll finished
        }
    }

    override fun onPageSelected(position: Int) {
        Log.d("ADAPTER", "PageSelected  position = $position")
        showingPosition = position
        showingFragment = getVisibleFragment(position)
    }

    init {
        val address = BibleApplication.instance.currentAddress
        viewpager.addOnPageChangeListener(this)
    }


    //start update
    // pra que serve?

    /*
    throw new IllegalStateException("The application's PagerAdapter changed the adapter's"
                    + " contents without calling PagerAdapter#notifyDataSetChanged!"
                    + " Expected adapter item count: " + mExpectedAdapterCount + ", found: " + N
                    + " Pager id: " + resName
                    + " Pager class: " + getClass()
                    + " Problematic adapter: " + mAdapter.getClass());
     */

    private fun getVisibleFragment(position: Int): ReadFragment? {
        val tag = "android:switcher:${viewpager.id}:$position"
        val currFrag = fm?.findFragmentByTag(tag)
        return currFrag as ReadFragment?
    }

    //TODO: implement destroy item to get rid of the old fragments???
    //if so, getItemPosition needs to return POSITION_NONE to force adapter to create fragmente always

    override fun getItem(position: Int): Fragment {
        //val item = fragments[position] as ReadFragment
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



//    fun moveForward() {
//        //remove previous
//        //move current backward
//        //add next
//
//        val newPrevious = fragments[CENTER] as ReadFragment
//        val newCurrent = fragments[RIGHT] as ReadFragment
//        val newNext = ReadFragment.newInstance(newCurrent.address.next())
//
//        Log.d("ADAPTER", "newPrevious address = ${newPrevious.address.asFullText()}")
//        Log.d("ADAPTER", "newCurrent address = ${newCurrent.address.asFullText()}")
//        Log.d("ADAPTER", "newNext address = ${newNext.address.asFullText()}")
//
//        fragments[LEFT] = newPrevious
//        fragments[CENTER] = newCurrent
//        fragments[RIGHT] = newNext
//
//        //BibleApplication.instance.updatePosition(newCurrent.address)
//
//        Log.d("ADAPTER", "call to notifyDataSetChanged")
//        //notifyDataSetChanged()
//        //viewpager.adapter = this
//        //viewpager.setCurrentItem(CENTER, false)
//    }



}