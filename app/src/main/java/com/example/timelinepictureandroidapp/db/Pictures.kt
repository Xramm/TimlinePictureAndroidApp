package com.example.timelinepictureandroidapp.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.timelinepictureandroidapp.Place
import java.net.URI

@Entity(foreignKeys = [(ForeignKey(entity = Place::class,
        parentColumns = ["placeId"],
        childColumns = ["picId"],
        onDelete = ForeignKey.CASCADE))])
data class Pictures(
        @PrimaryKey
        val picId: Long,
        @PrimaryKey
        val name: String,
        val thumpNail: Bitmap,
        val pictureUri: URI,
        val heading: String,
        val timeStamp: Long
)
