package com.claraboia.bibleandroid.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.ViewGroup
import com.claraboia.bibleandroid.BibleApplication
import com.claraboia.bibleandroid.fragments.ReadFragment
import com.claraboia.bibleandroid.helpers.asFullText
import com.claraboia.bibleandroid.helpers.next
import com.claraboia.bibleandroid.helpers.previous
import com.claraboia.bibleandroid.views.ReadViewPager

/**
 * Created by lucasbatagliao on 03/12/16.
 */
// FragmnetPagerAdapter does not destoy fragments, only detaches
class ReadViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    private val fragments = kotlin.arrayOfNulls<Fragment>(3)


    init {
        val address = BibleApplication.instance.currentAddress
        fragments[ReadViewPager.READ_POSITION.CURRENT.toInt()] = ReadFragment.newInstance(address)
        fragments[ReadViewPager.READ_POSITION.PREVIOUS.toInt()] = ReadFragment.newInstance(address.previous())
        fragments[ReadViewPager.READ_POSITION.NEXT.toInt()] = ReadFragment.newInstance(address.next())
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



    override fun getItem(position: Int): Fragment {
        val item = fragments[position] as ReadFragment
        Log.d("ADAPTER", "getItem position $position >> value >> ${item.address.asFullText()}")
        return item
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItemPosition(`object`: Any?): Int {
        //return correct position
        //object is a ReadFragment
        val readfrag = `object` as ReadFragment
        val position = fragments.indexOf(readfrag)

        Log.d("ADAPTER", "position of ${readfrag.address.asFullText()} == $position")

        if(position >= 0)
            return position
        else
            return PagerAdapter.POSITION_NONE
    }

    fun moveForward() {
        //remove previous
        //move current backward
        //add next

        val newPrevious = fragments[ReadViewPager.READ_POSITION.CURRENT.toInt()] as ReadFragment
        val newCurrent = fragments[ReadViewPager.READ_POSITION.NEXT.toInt()] as ReadFragment
        val newNext = ReadFragment.newInstance(newCurrent.address.next())

        Log.d("ADAPTER", "newPrevious address = ${newPrevious.address.asFullText()}")
        Log.d("ADAPTER", "newCurrent address = ${newCurrent.address.asFullText()}")
        Log.d("ADAPTER", "newNext address = ${newNext.address.asFullText()}")


        fragments[ReadViewPager.READ_POSITION.PREVIOUS.toInt()] = null
        fragments[ReadViewPager.READ_POSITION.PREVIOUS.toInt()] = newPrevious
        fragments[ReadViewPager.READ_POSITION.CURRENT.toInt()] = newCurrent
        fragments[ReadViewPager.READ_POSITION.NEXT.toInt()] = newNext

        BibleApplication.instance.updatePosition(newCurrent.address)

        Log.d("ADAPTER", "call to notifyDataSetChanged")
        notifyDataSetChanged()
    }

    /**
     * Called to inform the adapter of which item is currently considered to
     * be the "primary", that is the one show to the user as the current page.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position that is now the primary.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     */
    //    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
//        val fragment = `object` as Fragment
//        if (fragment !== mCurrentPrimaryItem) {
//            if (mCurrentPrimaryItem != null) {
//                mCurrentPrimaryItem.setMenuVisibility(false)
//                mCurrentPrimaryItem.userVisibleHint = false
//            }
//            if (fragment != null) {
//                fragment.setMenuVisibility(true)
//                fragment.userVisibleHint = true
//            }
//            mCurrentPrimaryItem = fragment
//        }
//    }


}