package com.example.timelinepictureandroidapp


import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.timelinepictureandroidapp.db.Pictures
import com.example.timelinepictureandroidapp.db.PlaceDB
import com.example.timelinepictureandroidapp.db.PlaceWithPictures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

object DataInDB: Application() {


    private val db by lazy { PlaceDB.get(this) }


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
    var tempPhoto: PlaceWithPictures? = null

    override fun onCreate() {
        super.onCreate()

        val db = PlaceDB.get(applicationContext)
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
     fun setpicture() {
         GlobalScope.launch {
             val ids = db.picturesDao().insert(Pictures(picId!!,thumpNail, pictureUri, heading, timeStamp, name))
             withContext(Dispatchers.Main){
                 Log.e("AAA","SAVED picture")
             }
         }
     }
}