package com.example.timelinepictureandroidapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlaceDao {
        @Query("SELECT * FROM place")
        fun getAll(): LiveData<List<Place>>
        //TODO more Querys and pictureDao
    }
