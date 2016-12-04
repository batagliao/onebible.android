package com.claraboia.bibleandroid.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import com.claraboia.bibleandroid.adapters.ReadViewPagerAdapter
import java.util.*

/**
 * Created by lucasbatagliao on 04/12/16.
 */

class ReadViewPager(context: Context?, attrs: AttributeSet?) : ViewPager(context, attrs) {

    enum class READ_POSITION {
        PREVIOUS,
        CURRENT,
        NEXT;

        fun toInt(): Int {
            return this.ordinal
        }
    }

    private val internalPageListener = InternalPageChangeListener()

    init {
        addOnPageChangeListener(internalPageListener)
    }

    constructor(context: Context?):this(context, null){}

    override fun addOnPageChangeListener(listener: OnPageChangeListener?) {
        if (listener is InternalPageChangeListener) {
            super.addOnPageChangeListener(listener)
        }else{
            if (listener != null) {
                internalPageListener.externalsPageListener.add(listener)
            }
        }
    }

    override fun removeOnPageChangeListener(listener: OnPageChangeListener?) {
        if(listener !is InternalPageChangeListener){
            internalPageListener.externalsPageListener.remove(listener)
        }
    }

    inner class InternalPageChangeListener : OnPageChangeListener{

        var externalsPageListener: MutableList<OnPageChangeListener> = mutableListOf()

        private var readPagerAdapter: ReadViewPagerAdapter?
            get(){
                return this@ReadViewPager.adapter as ReadViewPagerAdapter
            }
            set(value) {
                this@ReadViewPager.adapter = value
            }

        override fun onPageScrollStateChanged(state: Int) {
            Log.d("VIEWPAGER", "state = $state   currentItem = ${this@ReadViewPager.currentItem}")

            //scroll whas completed
            if(state == ViewPager.SCROLL_STATE_IDLE){
                if(this@ReadViewPager.currentItem == READ_POSITION.NEXT.toInt()){
                    //moved forward
                    readPagerAdapter?.moveForward()
                    this@ReadViewPager.setCurrentItem(READ_POSITION.CURRENT.toInt(), false)
//                    val oldAdapter = readPagerAdapter
//                    readPagerAdapter = null
//                    readPagerAdapter = oldAdapter

                    //maybe update position
                    //TODO:
                }else if(this@ReadViewPager.currentItem == READ_POSITION.PREVIOUS.toInt()){
                    //moved backward
                    //TODO:
                }else{ //middle fragment
                    // do nothing
                }
            }

            for (l in externalsPageListener){
                l.onPageScrollStateChanged(state)
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            for(l in externalsPageListener){
                l.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }

        override fun onPageSelected(position: Int) {
            for(l in externalsPageListener){
                l.onPageSelected(position)
            }
        }

    }
}