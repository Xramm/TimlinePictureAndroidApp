package com.example.timelinepictureandroidapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity(), LocationListener {
    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        if((Build.VERSION.SDK_INT >=23 && ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0)}

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()


    }

    override fun onLocationChanged(p0: Location?) {
        TODO("Not yet implemented")
    }

    private fun fetchLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ) {
                Log.d("AAA","WTH")
            getLocationUpdate()
        }else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

    private fun getLocationUpdate(){

        locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {

            override fun onLocationResult(p0: LocationResult) {
                Log.d("AAA","HELL ")
                if(p0.locations.isNotEmpty()){
                    val location = p0.lastLocation
                    Log.d("AAA","HELL $location")
                    if (location != null) {
                        val latLng = LatLng(location.latitude,location.longitude)
                        Log.d("AAA","CAN get here")
                        tvLat.text = "Lat: ${location.latitude}"
                        tvLong.text = "Long: ${location.longitude}"

                    }
                } else Log.d("AAA","SOmeeeee")
            }
        }
    }
}