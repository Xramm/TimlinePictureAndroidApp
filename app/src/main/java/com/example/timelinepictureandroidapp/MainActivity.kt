package com.example.timelinepictureandroidapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        main_screen_view_photos_button.setOnClickListener {
            val intent = Intent(this,GalleryActivity::class.java)
            startActivity(intent)
        }
    }
}