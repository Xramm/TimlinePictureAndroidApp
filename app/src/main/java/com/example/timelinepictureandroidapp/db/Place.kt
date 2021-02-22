package com.example.timelinepictureandroidapp

import android.graphics.Bitmap
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.net.URI

@Entity
data class Place(
    @PrimaryKey(autoGenerate = true)
    val placeId: Long,
    val name: String?,
    val info: String?,
    val latitude: Double,
    val longitude: Double,
)


