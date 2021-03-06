package com.example.timelinepictureandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat
import com.example.timelinepictureandroidapp.db.PlaceDB
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        main_screen_take_photo_button.setOnClickListener {
            val intent = Intent(this,PhotoActivity::class.java)
            startActivity(intent)
        }

        main_screen_view_map_button.setOnClickListener {
            val intent = Intent(this,MapActivity::class.java)
            startActivity(intent)
        }
    }
}