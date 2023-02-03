package com.code.wallpick.ui.intro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.viewpager2.widget.ViewPager2
import com.code.wallpick.R
import com.code.wallpick.adapter.OnBoardingAdapter
import com.code.wallpick.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var sliderAdapter: OnBoardingAdapter
    private lateinit var letsGetStarted: TextView
    private lateinit var skipButton: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        window.statusBarColor = getColorFromAttr(R.attr.background_color)


        viewPager = findViewById(R.id.slider)
        letsGetStarted = findViewById(R.id.get_started_btn)
        skipButton = findViewById(R.id.skip_btn)

        tabLayout = findViewById(R.id.dots)

        sliderAdapter = OnBoardingAdapter(this)
        viewPager.adapter = sliderAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) {
                tab.setIcon(R.drawable.selected_dot)

            } else if (position == 1) {
                tab.setIcon(R.drawable.selected_dot)
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    letsGetStarted.text = "Next"
                    letsGetStarted.setOnClickListener {
                        viewPager.setCurrentItem(position + 1, true)
                    }
                } else if (position == 1) {
                    letsGetStarted.text = "Get Started"
                    letsGetStarted.setOnClickListener {
                        startActivity(Intent(this@OnBoardingActivity,LoginActivity::class.java))
                    }
                }
            }
        })

        skipButton.setOnClickListener {
            startActivity(Intent(this@OnBoardingActivity,LoginActivity::class.java))
        }
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