package com.example.timelinepictureandroidapp

import android.graphics.Bitmap
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.net.URI

@Entity
data class Place(
    @PrimaryKey(autoGenerate = true)
    val placeId: Long,
    val name: String,
    val info: Long,
    val latitude: Long,
    val longitude: Long,
)

@Entity
data class Pictures(
    @PrimaryKey
    val picId: Long,
    val thumpNail: Bitmap,
    val pictureUri: URI,
    val heading: String,
    val timeStamp: Long
)

data class PlaceWithPictures (
    @Embedded val place: Place,
    @Relation(
        parentColumn = "placeId",
        entityColumn = "picId"
    )
    val pictures:List<Pictures>? = null
        )
