package com.example.timelinepictureandroidapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class PhotoActivity : AppCompatActivity() {

    lateinit var fragment1: choiceFragment

    val REQUEST_IMAGE_CAPTURE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        fragment1 = choiceFragment()
       loadFragment(fragment1)


        }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fcView, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }




    }
