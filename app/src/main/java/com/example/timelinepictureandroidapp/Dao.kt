package com.example.timelinepictureandroidapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlaceDao {
        @Query("SELECT * FROM place")
        fun getAll(): List<Place>

        @Query("SELECT * FROM place WHERE place.placeId = :picId")
        fun getPlaceWithPictures(picId: Long): PlaceWithPictures
    }

@Dao
interface PicturesDao {}