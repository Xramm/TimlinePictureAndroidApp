package com.example.timelinepictureandroidapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.timelinepictureandroidapp.db.Pictures
import com.example.timelinepictureandroidapp.db.PlaceDB

class ShowFragment : Fragment(R.layout.fragment_show) {

    companion object {
        public var showPictures : List<Pictures>? = null
    }

    var viewPager: ViewPager? = null

    lateinit var showAdapter : ShowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db by lazy { getView()?.context?.let { PlaceDB.get(it) } }

        val view = getView();

        if (view != null) {
            Toast.makeText(view.context, "Entered Show View", Toast.LENGTH_SHORT).show()
        }

        if (showPictures != null && view != null) {
            showAdapter = ShowAdapter(view.context, showPictures)
        }

        viewPager = getView()?.findViewById<ViewPager>(R.id.viewPager)

        viewPager!!.adapter = showAdapter
    }
}