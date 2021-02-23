package com.example.timelinepictureandroidapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.timelinepictureandroidapp.db.PlaceDB
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_choice.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class choiceFragment : Fragment(R.layout.fragment_choice) {

    private  lateinit var places : List<Place>
    private val db by lazy { PlaceDB.get(requireContext()) }
   // private val places: List<Place> = PlaceDB.get(requireActivity().getApplication()).placeDao().getAll()
    private var mLatitude: Double = 0.0 // A variable which will hold the latitude value.
    private var mLongitude: Double = 0.0 // A variable which will hold the longitude value.
    private lateinit var mFusedLocationClient: FusedLocationProviderClient // A fused location client variable which is further user to get the user's current location
    private val LOCATION_PERMISSION_REQUEST = 1
    val dataInDB = DataInDB()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch (Dispatchers.IO){
          places = db.placeDao().getAll()
            withContext(Dispatchers.Main){
            Log.e("AAA","AA $places")
        }}



        if((Build.VERSION.SDK_INT >=23 && ContextCompat.checkSelfPermission(
                        requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    0)}
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fetchLocation()

        take_photo_button.setOnClickListener {
            dataInDB.latitude=mLatitude
            dataInDB.longitude=mLongitude
               nextFrag()
        }

        take_photo_update_button.setOnClickListener {
            Log.e("WTH","WHAT IS HAPPENING")
            nextFrag()
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
    private fun nextFrag(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.fcView, PhotoFragment())

            transaction.disallowAddToBackStack()
            transaction.commit()
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
                for (place in places){
                    if ((mLatitude>=place.latitude-0.0005 && mLatitude <= place.latitude+0.0005)&&
                            mLongitude>=place.longitude-0.0005 && mLongitude <= place.longitude+0.0005){
                        dataInDB.picId = place.placeId
                        take_photo_or_text.visibility = View.VISIBLE
                        take_photo_update_button.visibility = View.VISIBLE
                    }
                }

        }
    }




}