package com.tubianto.imageslider

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.balysv.materialripple.MaterialRippleLayout
import com.bumptech.glide.Glide

/**
 * Created by Tubianto on 08/07/2021.
 */
class AdapterImageSlider(private val act: Activity, var items: ArrayList<Image>) : PagerAdapter() {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: Image?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getCount(): Int {
        return items.size
    }

    fun getItem(pos: Int): Image {
        return items[pos]
    }

    fun setItems(items: List<Image>) {
        this.items.apply {
            clear()
            addAll(items)
        }
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val obj: Image = items[position]
        val inflater = act.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_image_slider, container, false)
        val image = view.findViewById<View>(R.id.image) as ImageView
        val lytParent: MaterialRippleLayout = view.findViewById<View>(R.id.lyt_parent) as MaterialRippleLayout

        obj.image.let {
            Glide.with(act)
                .load(it)
                .into(image)
        }
        lytParent.setOnClickListener { v ->
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(v, obj)
            }

            Toast.makeText(act,obj.name,Toast.LENGTH_SHORT).show()
        }
        (container as ViewPager).addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        (container as ViewPager).removeView(obj as RelativeLayout)
    }
}