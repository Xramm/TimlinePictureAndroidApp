package com.example.timelinepictureandroidapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.timelinepictureandroidapp.db.Pictures

class ShowAdapter(var context: Context, var pictures: List<Pictures>?) : PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun getCount(): Int {
        return  pictures!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.show_item, container, false)
        val imageView = view.findViewById<View>(R.id.show_image_view) as ImageView

        imageView.setImageBitmap(pictures!!.get(position).thumpNail)

        container.addView(view)

        return view
    }

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}