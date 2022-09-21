package com.code.wallpick.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.code.wallpick.R
import com.code.wallpick.ui.home.HomeActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signUpButton: TextView
    private lateinit var loginButton: Button
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        emailLayout = findViewById(R.id.emailLayout)
        passwordLayout = findViewById(R.id.passwordLayout)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signUpTextView)
        signUpButton.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        loginButton.setOnClickListener {
            clearError()
            val email = emailLayout.editText?.text.toString()
            val password = passwordLayout.editText?.text.toString()
            if (!checkEmail(email)) {
                emailLayout.error = "Enter Valid Email"
            } else if (password.isBlank() || password.isEmpty()) {
                passwordLayout.error = "Enter your password"
                passwordLayout.errorIconDrawable = null
            } else {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    progressBar.visibility = View.INVISIBLE
                    if (it.isComplete && it.isSuccessful) {
                        if (auth.currentUser!!.isEmailVerified) {
                            startActivity(Intent(this,HomeActivity::class.java))
                            clearScreen()
                        } else {
                            emailLayout.error = "Email is not verified"
                        }
                    } else {
                        when(it.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                emailLayout.error = "User does not exist"
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                passwordLayout.error = "Invalid password"
                                passwordLayout.errorIconDrawable = null
                            }
                        }
                    }
                }
            }
        }

    }

    private fun clearScreen() {
        emailLayout.editText?.setText("")
        passwordLayout.editText?.setText("")
    }

    private fun clearError() {
        emailLayout.isErrorEnabled = false
        passwordLayout.isErrorEnabled = false
    }

    private fun checkEmail(email: String): Boolean {
        return email.contains('@')
    }
}