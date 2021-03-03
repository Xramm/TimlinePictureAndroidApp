package com.example.timelinepictureandroidapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_photo_info_edit.*
import kotlinx.android.synthetic.main.photo_info_edit_layout.*
import kotlinx.android.synthetic.main.photo_info_edit_layout.photo_info_edit_edit_info
import kotlinx.android.synthetic.main.photo_info_edit_layout.photo_info_edit_edit_name
import kotlinx.android.synthetic.main.photo_info_edit_layout.photo_info_edit_retake_button
import kotlinx.android.synthetic.main.photo_info_edit_layout.photo_info_edit_save_button
import java.time.LocalDateTime

class PhotoInfoEdit : Fragment(R.layout.fragment_photo_info_edit) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imView.setImageBitmap(DataInDB.thumpNail)
        photo_info_edit_save_button.setOnClickListener {
            DataInDB.name = photo_info_edit_edit_name.toString()
            DataInDB.info = photo_info_edit_edit_info.toString()
            DataInDB.timeStamp = LocalDateTime.now()
            Log.e("qwe","1 ${DataInDB.info}")
            Log.e("qwe","2 ${DataInDB.name}")
            Log.e("qwe","3 ${DataInDB.latitude}")
            Log.e("qwe","4 ${DataInDB.longitude}")
            Log.e("qwe","5 ${DataInDB.heading}")
            Log.e("qwe","6 ${DataInDB.picId}")
            Log.e("qwe","7 ${DataInDB.pictureUri}")
            Log.e("qwe","8 ${DataInDB.placeId}")
            Log.e("qwe","9 ${DataInDB.thumpNail}")
            Log.e("qwe","10 ${DataInDB.timeStamp}")

          //  DataInDB.setplace()
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }
        photo_info_edit_retake_button.setOnClickListener {

                val transaction = activity?.supportFragmentManager?.beginTransaction()
                if (transaction != null) {
                    transaction.replace(R.id.fcView, PhotoFragment())

                    transaction.disallowAddToBackStack()
                    transaction.commit()
                }
        }

    }


}