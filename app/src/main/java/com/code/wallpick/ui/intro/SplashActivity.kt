package com.code.wallpick.ui.intro

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.code.wallpick.App
import com.code.wallpick.R
import com.code.wallpick.service.ShakeService
import com.code.wallpick.ui.home.HomeActivity
import com.code.wallpick.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import java.io.File


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



        initStorage()

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animaiton)

        image = findViewById(R.id.splashImageView)
        head = findViewById(R.id.signUpHeading)
        body = findViewById(R.id.splashBodyTextView)

        image.animation = topAnimation
        head.animation = bottomAnimation
        body.animation = bottomAnimation

        navigate()
    }

    private fun navigate() {

        Handler().postDelayed({

            val onBoarding = getSharedPreferences(App.PREFERENCES,MODE_PRIVATE)
            val isFirstTime = onBoarding.getBoolean("firstTime",false)

            Log.d("Splashing",isFirstTime.toString())

            if (!isFirstTime) {
                val editor = onBoarding.edit()
                editor.putBoolean("firstTime",false)
                editor.apply()

                startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                this@SplashActivity.finish()
            } else {
                if (FirebaseAuth.getInstance().currentUser != null) {
                    startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                    this@SplashActivity.finish()
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    this@SplashActivity.finish()
                }
            }
        }, splashDisplayLength)
    }


    private fun initStorage() {
        val dir = File("/data/data/com.code.wallpick/files")
        dir.mkdir()

        val file = File(App.PATH)
        if (!file.exists()) {
            file.mkdir()
        }

        val child = File(App.PATH,App.FAVOURITE)
        child.mkdir()
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