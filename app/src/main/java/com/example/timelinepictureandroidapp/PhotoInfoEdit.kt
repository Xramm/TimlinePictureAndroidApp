package com.example.timelinepictureandroidapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.photo_info_edit_layout.*

class PhotoInfoEdit : Fragment(R.layout.fragment_photo_info_edit) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photo_info_edit_save_button.setOnClickListener {
            DataInDB.name = photo_info_edit_edit_name.toString()
            DataInDB.info = photo_info_edit_edit_info.toString()
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