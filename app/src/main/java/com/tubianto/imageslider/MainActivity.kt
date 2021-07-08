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

    private val arrayImagePlace = intArrayOf(
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4,
        R.drawable.image_5
    )

    private val arrayTitlePlace = arrayOf(
        "Dui fringilla ornare finibus, orci odio",
        "Mauris sagittis non elit quis fermentum",
        "Mauris ultricies augue sit amet est sollicitudin",
        "Suspendisse ornare est ac auctor pulvinar",
        "Vivamus laoreet aliquam ipsum eget pretium"
    )

    private val arrayPlace = arrayOf(
        "Foggy Hill",
        "The Backpacker",
        "River Forest",
        "Mist Mountain",
        "Side Park"
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
    }

    private fun setupUI() {
        adapterImageSlider = AdapterImageSlider(this, ArrayList<Image>())
        val items: MutableList<Image> = ArrayList<Image>()
        for (i in arrayImagePlace.indices) {
            val obj = Image()
            obj.image = arrayImagePlace[i]
            obj.imageDrw = resources.getDrawable(obj.image)
            obj.name = arrayTitlePlace[i]
            obj.place = arrayPlace[i]
            items.add(obj)
        }
        adapterImageSlider!!.setItems(items)
        viewPager!!.adapter = adapterImageSlider

        // displaying selected image first
        viewPager!!.currentItem = 0
        addBottomDots(llDots, adapterImageSlider!!.count, 0)
        tvTitle?.text = items[0].name
        tvPlace?.text = items[0].place
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                pos: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(pos: Int) {
                tvTitle?.text = items[pos].name
                tvPlace?.text = items[pos].place
                addBottomDots(llDots, adapterImageSlider!!.count, pos)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        startAutoSlider(adapterImageSlider!!.count)
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