package com.example.timelinepictureandroidapp

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.SurfaceTexture
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.TextureView
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.File


class PhotoFragment : Fragment(R.layout.fragment_photo), TextureView.SurfaceTextureListener {


    val REQUEST_IMAGE_CAPTURE = 99
    var imageFile: File? = null
    lateinit var photoURI : Uri
    lateinit var  mCurrentPhotoPath : String


    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createFile()
        createUri()
        var mCurrentPhotoPath = imageFile!!.absolutePath
        btnClick.setOnClickListener{

            takephoto()
        }
    }

    private fun createFile(){
    val fileName = "temp_photo"
    val imgPath = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    imageFile = File.createTempFile(fileName, ".jpg", imgPath )
    mCurrentPhotoPath = imageFile!!.absolutePath
}

    private fun createUri(){
    photoURI = FileProvider.getUriForFile(requireContext(),
        "com.example.cameratest.fileprovider",
        imageFile!!)
}


    fun takephoto(){
    val myIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (activity?.let { myIntent.resolveActivity(it.packageManager) } != null) {
        myIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        myIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(myIntent, REQUEST_IMAGE_CAPTURE)
    }

}



    override fun onActivityResult(requestCode: Int, resultCode: Int, recIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, recIntent)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath) // imageFile.absolutePath
            ivImage.setImageBitmap(imageBitmap)
    }
}
}


