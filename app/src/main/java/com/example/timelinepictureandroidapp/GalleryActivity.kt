package com.example.timelinepictureandroidapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.timelinepictureandroidapp.db.PlaceDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GalleryActivity : AppCompatActivity(),ListViewFragment.FirstFragmentListener {

    private val db by lazy { PlaceDB.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val firstFragment = ListViewFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            commit()
        }

    }

    override fun onButtonClick(position: Int) {

        GlobalScope.launch(Dispatchers.IO) {
            ShowFragment.showPictures = db.placeDao().getPlaceWithPictures(position.toLong() + 1).pictures
        }

        val secondFragment = ShowFragment()
        Log.d("SHIT","WTF $position")
        val bundle = Bundle()
        bundle.putInt("pos",position)
        secondFragment.arguments = bundle
        Log.d("SHIT","FUCK $bundle")
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, secondFragment).addToBackStack(null)
            commit()
        }
    }


}