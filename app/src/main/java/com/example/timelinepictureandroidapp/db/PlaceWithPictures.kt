package com.example.timelinepictureandroidapp.db

import androidx.room.Embedded
import androidx.room.Relation
import com.example.timelinepictureandroidapp.Place


data class PlaceWithPictures (
        @Embedded val place: Place,
        @Relation(
                parentColumn = "placeId",
                entityColumn = "picId"
        )
        val pictures: List<Pictures>? = null
)

