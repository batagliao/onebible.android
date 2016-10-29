package com.claraboia.bibleandroid.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claraboia.bibleandroid.R
import com.claraboia.bibleandroid.adapters.TranslationRecyclerAdapter
import com.claraboia.bibleandroid.models.BibleTranslation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cloud_translations.*
import java.util.*

/**
 * Created by lucasbatagliao on 26/10/16.
 */
class CloudTranslationsFragment : Fragment() {

    private val database by lazy { FirebaseDatabase.getInstance().reference }

    private val translations: MutableList<BibleTranslation> = ArrayList()
    private val adapter = TranslationRecyclerAdapter(translations)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_cloud_translations, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        translationCloudList.visibility = View.GONE
        translationCloudList.layoutManager = LinearLayoutManager(activity)
        translationCloudList.adapter = adapter
        translationCloudList.setHasFixedSize(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val query = database.child("translations").orderByChild("abbreviation")
        query.addListenerForSingleValueEvent(listenerForDatabase())
    }

    inner class listenerForDatabase: ValueEventListener{
        override fun onCancelled(error: DatabaseError?) {
            Log.e("CloudTranslationsFragme", error.toString())
        }

        override fun onDataChange(snapshot: DataSnapshot?) {
            translations.clear()
            for(translationsnapshot in snapshot!!.children){
                val translation = translationsnapshot.getValue(BibleTranslation::class.java)
                translations.add(translation)
            }
            adapter.updateData(translations)
            translationCloudList.visibility = View.VISIBLE
            progress_bar.visibility = View.GONE
        }

    }
}