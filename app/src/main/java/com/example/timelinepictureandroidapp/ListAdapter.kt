package com.example.timelinepictureandroidapp

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.timelinepictureandroidapp.db.PlaceDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListAdapter(private val context: Context, private val dataSource: List<Place>): BaseAdapter() {

    private lateinit var name: TextView
    private lateinit var info: TextView
    private lateinit var lat: TextView
    private lateinit var long: TextView
    private lateinit var image: ImageView

    private val db by lazy { PlaceDB.get(context) }
    private var temp: Bitmap? = null

    override fun getCount(): Int {
        // TODO: 10.3.2021
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
       return position
    }

    override fun getItemId(position: Int): Long {
        return  dataSource[position].placeId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        GlobalScope.launch(Dispatchers.IO) {
            temp = db.placeDao()
                .getPlaceWithPictures(dataSource[position].placeId).pictures?.get(0)?.thumpNail!!
        }
            var convertView = convertView
            convertView = LayoutInflater.from(context).inflate(R.layout.lvitems, parent, false)
            name = convertView.findViewById(R.id.txname)
            info = convertView.findViewById(R.id.txinfo)
            lat = convertView.findViewById(R.id.txlat)
            long = convertView.findViewById(R.id.txlong)
            image = convertView.findViewById(R.id.ivitem)

            name.text = dataSource[position].name
            info.text = dataSource[position].info
            lat.text = dataSource[position].latitude.toString()
            long.text = dataSource[position].longitude.toString()
            image.setImageBitmap(temp)
            return convertView

    }
}