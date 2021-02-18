package com.example.timelinepictureandroidapp.db

import android.graphics.Bitmap
import android.net.Uri
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
        val picId: Long,
        val thumpNail: String,
        val pictureUri: String,
        val heading: String,
        val timeStamp: Long,
        @PrimaryKey
        val name: String,
)
