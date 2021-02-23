package com.example.timelinepictureandroidapp


import android.app.Application
import androidx.appcompat.app.AppCompatActivity

import com.example.timelinepictureandroidapp.db.PlaceDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.core.content.ContextCompat
import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataInDB: Application() {


    private val db by lazy { PlaceDB.get(context = baseContext) }


    var placeId: Long =0
    var name: String =""
    var info: String =""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var picId: Long? = null
    var thumpNail: String = ""
    var pictureUri: String= ""
    var heading: String = ""
    var timeStamp: Long = 0

    override fun onCreate() {
        super.onCreate()
    }

     fun set() {
         GlobalScope.launch {
             val id = db.placeDao().insert(Place(0, name, info, latitude, longitude))
             withContext(Dispatchers.Main){
                 Log.e("AAA","SAVED to database")
             }
         }
     }

}