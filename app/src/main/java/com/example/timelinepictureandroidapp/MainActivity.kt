package com.example.timelinepictureandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnPhoto.setOnClickListener {
            val intent = Intent(this,PhotoActivity::class.java)
            startActivity(intent)
        }
    }
}