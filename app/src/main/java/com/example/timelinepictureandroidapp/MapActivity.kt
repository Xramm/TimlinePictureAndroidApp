package com.example.timelinepictureandroidapp

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.timelinepictureandroidapp.db.PlaceDB
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
        GlobalScope.async(Dispatchers.IO ) {
            places = db.placeDao().getAll()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.frMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        var maxLat = -90.0
        var minLat = 90.0
        var maxLong = -180.0
        var minLong = 180.0
       map = p0.apply {
           for (place in places) {
               if (place.latitude<minLat) minLat = place.latitude
               if (place.latitude>maxLat) maxLat = place.latitude
               if (place.longitude<minLong) minLong = place.longitude
               if (place.latitude>maxLong) maxLong = place.longitude

               addMarker(
                       MarkerOptions()
                               .position(LatLng( place.latitude,place.longitude))
                               .title(place.name.toString())
                               .snippet(place.info.toString())

               )
           }
       }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng((maxLat+minLat)/2,(maxLong+minLong)/2),5f))
    }


}