package com.example.timelinepictureandroidapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Place(
    @PrimaryKey(autoGenerate = true)
    val placeId: Long,
    val name: String?,
    val info: String?,
    val latitude: Double,
    val longitude: Double,
)


