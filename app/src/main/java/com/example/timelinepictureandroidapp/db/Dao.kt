package com.example.timelinepictureandroidapp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.timelinepictureandroidapp.db.Pictures
import com.example.timelinepictureandroidapp.db.PlaceWithPictures

@Dao
interface PlaceDao {
        @Query("SELECT * FROM place")
        fun getAll(): List<Place>

        @Query("SELECT * FROM place WHERE place.placeId = :picId")
        fun getPlaceWithPictures(picId: Long): PlaceWithPictures

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(place: Place): Long

        @Delete
        fun delete(place: Place)
    }

@Dao
interface PicturesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Pictures): Long

    @Delete
    fun delete(place: Pictures)

}