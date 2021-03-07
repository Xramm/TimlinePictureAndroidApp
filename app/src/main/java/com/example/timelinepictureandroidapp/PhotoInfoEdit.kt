package com.example.timelinepictureandroidapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_photo_info_edit.*
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
            DataInDB.name = photo_info_edit_edit_name.text.toString()
            DataInDB.info = photo_info_edit_edit_info.text.toString()
            DataInDB.timeStamp = LocalDateTime.now()

            if (DataInDB.picId == null){
            DataInDB.setplace()}else{
                DataInDB.setpicture()
            }
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