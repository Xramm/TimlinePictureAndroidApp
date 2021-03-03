package com.example.timelinepictureandroidapp


import android.app.Application
import androidx.appcompat.app.AppCompatActivity

import com.example.timelinepictureandroidapp.db.PlaceDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.core.content.ContextCompat
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.timelinepictureandroidapp.db.Pictures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

object DataInDB: Application() {


    private val db by lazy { PlaceDB.get(context = baseContext) }


    var placeId: Long =0
    var name: String =""
    var info: String =""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var picId: Long? = null
    lateinit var thumpNail: Bitmap
    lateinit var pictureUri: Uri
    var heading: String = ""
    var timeStamp: LocalDateTime? = null

    override fun onCreate() {
        super.onCreate()
    }

     fun setplace() {
         GlobalScope.launch {
             val id = db.placeDao().insert(Place(0, name, info, latitude, longitude))
             db.picturesDao().insert(Pictures(id, thumpNail, pictureUri, heading, timeStamp, name))
             withContext(Dispatchers.Main){
                 Log.e("AAA","SAVED to database")
             }
         }
     }
     fun setpicture(id: Int) {
         GlobalScope.launch {
             val ids = db.picturesDao().insert(Pictures(picId!!,thumpNail, pictureUri, heading, timeStamp, name))
         }
     }
}