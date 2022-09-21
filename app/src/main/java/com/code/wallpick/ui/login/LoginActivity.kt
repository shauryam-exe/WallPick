package com.code.wallpick.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.code.wallpick.R
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signUpButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signUpButton = findViewById(R.id.signUpTextView)
        signUpButton.setOnClickListener {
            
        }

        auth = FirebaseAuth.getInstance()
    }
}