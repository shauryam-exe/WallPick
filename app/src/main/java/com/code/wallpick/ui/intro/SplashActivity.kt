package com.code.wallpick.ui.intro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.code.wallpick.R
import com.code.wallpick.ui.home.HomeActivity
import com.google.firebase.FirebaseApp

class SplashActivity : AppCompatActivity() {

    lateinit var topAnimation: Animation
    lateinit var bottomAnimation: Animation

    lateinit var image: ImageView
    lateinit var head: TextView
    lateinit var body: TextView

    private val splashDisplayLength: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = getColorFromAttr(R.attr.background_color)


        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animaiton)

        image = findViewById(R.id.splashImageView)
        head = findViewById(R.id.signUpHeading)
        body = findViewById(R.id.splashBodyTextView)

        image.animation = topAnimation
        head.animation = bottomAnimation
        body.animation = bottomAnimation

        Handler().postDelayed({

            startActivity(Intent(this@SplashActivity,HomeActivity::class.java))
            this@SplashActivity.finish()
        }, splashDisplayLength)
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