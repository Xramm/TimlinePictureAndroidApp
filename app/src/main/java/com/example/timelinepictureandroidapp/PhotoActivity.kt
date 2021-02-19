package com.example.timelinepictureandroidapp

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.timelinepictureandroidapp.db.PlaceDB
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST = 1
    private  val REQUEST_IMAGE_CAPTURE = 99
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
        fusedLocationClient.lastLocation.addOnCompleteListener(this){
            task -> if (task.isSuccessful && task.result != null) {
               tvLat.text = task.result.latitude.toString()
            tvLong.text = task.result.longitude.toString()
           // TODO go trough dao and get if user have this position in database

        }
        }

        btnPhoto.setOnClickListener { takePhoto() }

    }
    private fun takePhoto(){
        Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
        .withListener(object: MultiplePermissionsListener{
            override fun onPermissionsChecked(report: MultiplePermissionsReport?){
                if (report!!.areAllPermissionsGranted()){
                    val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    Log.d("AAA","AAAAA")
                   startActivityForResult(photoIntent,REQUEST_IMAGE_CAPTURE )
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread()
                .check()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
                .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
                .setPositiveButton("GO TO SETTINGS"
                ) { _, _ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss()
                }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
            val extras = data!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            ivImage.setImageBitmap(imageBitmap)
        }
    }
}