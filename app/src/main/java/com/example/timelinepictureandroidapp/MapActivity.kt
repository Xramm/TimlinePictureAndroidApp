package com.example.timelinepictureandroidapp

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.timelinepictureandroidapp.db.PlaceDB
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class MapActivity : FragmentActivity(),OnMapReadyCallback {


    private lateinit var map: GoogleMap
    private  lateinit var places : List<Place>
    private val db by lazy { PlaceDB.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        GlobalScope.async(Dispatchers.IO) {
            places = db.placeDao().getAll()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.frMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        val latlngBuilder = LatLngBounds.Builder()

       map = p0.apply {
           for (place in places) {
               latlngBuilder.include(LatLng(place.latitude,place.longitude))

               addMarker(
                       MarkerOptions()
                               .position(LatLng(place.latitude, place.longitude))
                               .title(place.name.toString())
                               .snippet(place.info.toString())

               )
           }
       }

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBuilder.build(),100))
    }


}