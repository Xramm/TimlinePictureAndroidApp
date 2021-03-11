package com.example.timelinepictureandroidapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.timelinepictureandroidapp.db.PlaceDB
import kotlinx.android.synthetic.main.fragment_list_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewFragment : Fragment(R.layout.fragment_list_view) {

    internal var activityCallBack: FirstFragmentListener? = null

    interface FirstFragmentListener {
        fun onButtonClick(position: Int)
    }

    private  lateinit var places : List<Place>
    private val db by lazy { PlaceDB.get(requireContext()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityCallBack = context as FirstFragmentListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val listView = lvPlaces
        GlobalScope.launch(Dispatchers.IO){
            places = db.placeDao().getAll()
            withContext(Dispatchers.Main) {
                Log.e("AAA", "AA $places")

                Log.e("qwe", "sdshd $places")
                val arrayAdapter = ListAdapter(requireContext(),places)

        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { adapterView, view, i, l ->

            activityCallBack!!.onButtonClick(i)
            Toast.makeText(requireContext(),"$i", Toast.LENGTH_SHORT).show()
        }}}
    }


}