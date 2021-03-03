package com.example.timelinepictureandroidapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.timelinepictureandroidapp.DataInDB
import com.example.timelinepictureandroidapp.PicturesDao
import com.example.timelinepictureandroidapp.Place
import com.example.timelinepictureandroidapp.PlaceDao


@Database(entities = [(Place::class),(Pictures::class)],version = 3)
@TypeConverters(Converters::class)
abstract class PlaceDB: RoomDatabase(){
    abstract fun placeDao(): PlaceDao
    abstract fun picturesDao(): PicturesDao

    companion object {
        private var sInstance: PlaceDB? = null
        @Synchronized
        fun get(context: Context): PlaceDB {
            if (sInstance == null){
                sInstance = Room.databaseBuilder(context.applicationContext,
                PlaceDB::class.java, "places.db").fallbackToDestructiveMigration().build()
            }
            return sInstance!!
        }
    }
}