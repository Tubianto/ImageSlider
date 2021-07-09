package com.tubianto.imageslider

import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * Created by Tubianto on 08/07/2021.
 */
class MainActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var llDots: LinearLayout? = null
    private var tvTitle: TextView? = null
    private var tvPlace: TextView? = null
    private var adapterImageSlider: AdapterImageSlider? = null
    private var runnable: Runnable? = null
    private val handler = Handler()

    private var dataItems = arrayListOf<Image>(
        Image(R.drawable.image_1,"Dui fringilla ornare finibus, orci odio","Foggy Hill"),
        Image(R.drawable.image_2,"Mauris sagittis non elit quis fermentum","The Backpacker"),
        Image(R.drawable.image_3,"Mauris ultricies augue sit amet est sollicitudin","River Forest"),
        Image(R.drawable.image_4,"Suspendisse ornare est ac auctor pulvinar","Mist Mountain"),
        Image(R.drawable.image_5,"Vivamus laoreet aliquam ipsum eget pretium","Side Park")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setupUI()
    }

    private fun init() {
        viewPager = findViewById(R.id.pager)
        llDots = findViewById(R.id.ll_dots)
        tvTitle = findViewById(R.id.tv_title)
        tvPlace = findViewById(R.id.tv_place)
        adapterImageSlider = AdapterImageSlider(this, arrayListOf())
        viewPager!!.adapter = adapterImageSlider
    }

    private fun setupUI() {
        getData()

        // displaying selected image first
        viewPager!!.currentItem = 0
        addBottomDots(llDots, adapterImageSlider!!.count, 0)
        tvTitle?.text = adapterImageSlider!!.getItem(0).name
        tvPlace?.text = adapterImageSlider!!.getItem(0).place

        handleViewPager()

        startAutoSlider(adapterImageSlider!!.count)
    }

    private fun getData() {
        retrieveList(dataItems)
    }

    private fun retrieveList(items: List<Image>) {
        adapterImageSlider?.apply {
            setItems(items)
            notifyDataSetChanged()
        }
    }

    private fun handleViewPager() {
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                pos: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(pos: Int) {
                tvTitle?.text = adapterImageSlider!!.getItem(pos).name
                tvPlace?.text = adapterImageSlider!!.getItem(pos).place
                addBottomDots(llDots, adapterImageSlider!!.count, pos)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun addBottomDots(llDots: LinearLayout?, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        llDots!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(15, 15))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle_outline)
            llDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[current]!!.setImageResource(R.drawable.shape_circle)
        }
    }

    private fun startAutoSlider(count: Int) {
        runnable = Runnable {
            var pos = viewPager!!.currentItem
            pos += 1
            if (pos >= count) pos = 0
            viewPager!!.currentItem = pos
            handler.postDelayed(runnable!!, 3000)
        }
        handler.postDelayed(runnable!!, 3000)
    }

    override fun onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable!!)
        super.onDestroy()
    }
}