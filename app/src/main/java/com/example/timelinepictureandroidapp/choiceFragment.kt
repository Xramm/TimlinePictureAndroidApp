package com.example.timelinepictureandroidapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.timelinepictureandroidapp.db.PlaceDB
import com.example.timelinepictureandroidapp.db.PlaceDB.Companion.get
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_choice.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.internal.artificialFrame
import kotlinx.coroutines.launch


class choiceFragment : Fragment(R.layout.fragment_choice) {

    private val db by lazy { PlaceDB.get(requireContext()) }
    private var mLatitude: Double = 0.0 // A variable which will hold the latitude value.
    private var mLongitude: Double = 0.0 // A variable which will hold the longitude value.
    private lateinit var mFusedLocationClient: FusedLocationProviderClient // A fused location client variable which is further user to get the user's current location
    private val LOCATION_PERMISSION_REQUEST = 1



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if((Build.VERSION.SDK_INT >=23 && ContextCompat.checkSelfPermission(
                        requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    0)}
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fetchLocation()

        take_photo_button.setOnClickListener {
            GlobalScope.launch {
                val id = db.placeDao().insert(Place(0,null,null,mLatitude,mLongitude))
                fragmentManager?.popBackStack()
            }
        }
        }

    private fun fetchLocation(){
        if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ) {
            requestNewLocationData()
        }else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

        /**
         * A function to request the current location. Using the fused location provider client.
         */
        @SuppressLint("MissingPermission")
        private fun requestNewLocationData() {

            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 0
            mLocationRequest.fastestInterval = 0
            mLocationRequest.numUpdates = 1

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            mFusedLocationClient.requestLocationUpdates(
                    mLocationRequest, mLocationCallback,
                    Looper.myLooper()
            )
        }

        /**
         * A location callback object of fused location provider client where we will get the current location details.
         */
        private val mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val mLastLocation: Location = locationResult.lastLocation
                mLatitude = mLastLocation.latitude
                Log.e("Current Latitude", "$mLatitude")
                mLongitude = mLastLocation.longitude
                Log.e("Current Longitude", "$mLongitude")


        }
    }




}