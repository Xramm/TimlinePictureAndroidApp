
package com.example.timelinepictureandroidapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class PhotoFragment : Fragment(R.layout.fragment_photo) {


    var opaque = 100

    private lateinit var safeContext : Context
    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService


    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                    requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            startCamera()
        }

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                opaque = progress
                ivGhost.setAlpha(opaque)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        // Set up the listener for take photo button
        camera_capture_button.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
                outputDirectory,
                SimpleDateFormat(FILENAME_FORMAT, Locale.US
                ).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(safeContext), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                val savedUri = Uri.fromFile(photoFile)
                val bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photoFile.absolutePath), 300, 300)
                DataInDB.thumpNail = bitmap
                DataInDB.pictureUri = savedUri

                val msg = "Photo capture succeeded: $savedUri"
                Toast.makeText(safeContext, msg, Toast.LENGTH_SHORT).show()
                Log.d(TAG, msg)
                Log.e("qwe", "$bitmap")

                val transaction = activity?.supportFragmentManager?.beginTransaction()
                if (transaction != null) {
                    transaction.replace(R.id.fcView, PhotoInfoEdit())

                    // transaction.disallowAddToBackStack()
                    transaction.commit()
                }


            }
        })
    }

    private fun startCamera() {

            val cameraProviderFuture = ProcessCameraProvider.getInstance(safeContext)
        // if tepmPhoto have picture do a ghost image
            if (DataInDB.tempPhoto != null) {
                slider.visibility = View.VISIBLE
                GlobalScope.launch(Dispatchers.IO) {
                    val temp = DataInDB.tempPhoto!!.pictures?.get(0)?.thumpNail
                    withContext(Dispatchers.Main) {
                        ivGhost.setImageBitmap(temp)

                        Log.e("ADS", "opague $opaque")
                    }
                }
            }

            cameraProviderFuture.addListener(Runnable {
                // Used to bind the lifecycle of cameras to the lifecycle owner
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                // Preview
                val preview = Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(viewFinder.surfaceProvider)
                        }

                imageCapture = ImageCapture.Builder()
                        .build()
                // Select back camera as a default
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                            requireActivity(), cameraSelector, preview, imageCapture)

                } catch (exc: Exception) {
                    Log.e(TAG, "Use case binding failed", exc)
                }

            }, ContextCompat.getMainExecutor(safeContext))

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                safeContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else safeContext.filesDir
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS){
            if (allPermissionsGranted()) {
                startCamera()
            }else{
                Toast.makeText(safeContext,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
              //  finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


}

