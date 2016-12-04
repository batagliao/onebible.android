package com.claraboia.bibleandroid.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.bibleApplication
import com.claraboia.bibleandroid.helpers.asFullText
import com.claraboia.bibleandroid.helpers.getAddressText
import com.claraboia.bibleandroid.models.BibleAddress
import kotlinx.android.synthetic.main.app_bar_read.*
import kotlinx.android.synthetic.main.fragment_read.*


const val BIBLEADDRESS_FRAGMENT_KEY = "fragment_address_key"

class ReadFragment : Fragment() {

    companion object {
        fun newInstance(address: BibleAddress): ReadFragment {
            val args = Bundle()
            args.putParcelable(BIBLEADDRESS_FRAGMENT_KEY, address)
            val fragment = ReadFragment()
            fragment.arguments = args
            return fragment
        }
    }


    val address: BibleAddress
        get() {
            return arguments.getParcelable<BibleAddress>(BIBLEADDRESS_FRAGMENT_KEY)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_read, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadText()
    }

    private fun loadText() {
        //convert actual position to address

        //TODO: set title
        //readTitle.text = address.asFullText()


        //loads corresponding text
        val text = activity.bibleApplication.currentBible.getAddressText(activity, address)

        //set text to view
        txtview.text = text


    }
}
