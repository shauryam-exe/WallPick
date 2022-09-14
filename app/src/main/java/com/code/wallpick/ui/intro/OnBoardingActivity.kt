package com.code.wallpick.ui.intro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.viewpager2.widget.ViewPager2
import com.code.wallpick.R
import com.code.wallpick.adapter.OnBoardingAdapter

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager2
    private lateinit var dotsLayout: LinearLayout
    private lateinit var sliderAdapter: OnBoardingAdapter
    private lateinit var dots: Array<TextView?>
    private lateinit var letsGetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        window.statusBarColor = getColorFromAttr(R.attr.background_color)


        viewPager = findViewById(R.id.slider)
        letsGetStarted = findViewById(R.id.get_started_btn)

        dotsLayout = findViewById(R.id.dots)
        addDots(1)

        sliderAdapter = OnBoardingAdapter()
        viewPager.adapter = sliderAdapter

        viewPager.registerOnPageChangeCallback(changeListener)
    }

    private fun addDots(position: Int) {
        dots = arrayOfNulls(2)
        dotsLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml("&#8226;")
            dots[i]?.textSize = 35f
            dotsLayout.addView(dots[i])
        }

        if (dots.size >= 0) {
            dots[position]?.setTextColor(resources.getColor(R.color.red))
        }
    }

    private var changeListener: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            addDots(position)
            if (position == 0) {
                letsGetStarted.text = "Next"
                letsGetStarted.setOnClickListener{
                    viewPager.setCurrentItem(1,true)
                }
            } else if (position == 1){
                letsGetStarted.text = "Get Started"
                letsGetStarted.setOnClickListener{
                    //startActivity(Intent(this@OnBoardingActivity, WhoAreYou::class.java))
                    //finish()
                    Log.d("check for errors","get started button working")
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    @ColorInt
    fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }
}