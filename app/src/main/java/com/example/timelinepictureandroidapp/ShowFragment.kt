package com.example.timelinepictureandroidapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.timelinepictureandroidapp.db.PlaceDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class ShowFragment : Fragment(R.layout.fragment_show) {

    var viewPager: ViewPager? = null

    lateinit var showAdapter : ShowAdapter

    private  lateinit var places : List<Place>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db by lazy { getView()?.context?.let { PlaceDB.get(it) } }

        val view = getView();

        if (view != null) {
            Toast.makeText(view.context, "Entered Show View", Toast.LENGTH_SHORT).show()
        }


        //TODO: Nothing wants to show up on the view pager?
        GlobalScope.async(Dispatchers.IO) {
            places = db!!.placeDao().getAll()

            loadImages(db!!, view)
        }

        viewPager = getView()?.findViewById<ViewPager>(R.id.viewPager)
    }

    private fun loadImages(db : PlaceDB, view : View?) {
        if (view != null) {
            showAdapter = ShowAdapter(view.context, db!!.placeDao().getPlaceWithPictures(0).pictures)
        }

        viewPager!!.adapter = showAdapter
    }
}