package com.code.wallpick.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.code.wallpick.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signUpButton: Button
    private lateinit var nameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUpButton = findViewById(R.id.signUpButton)
        nameLayout = findViewById(R.id.nameLayout)
        emailLayout = findViewById(R.id.emailLayout)
        passwordLayout = findViewById(R.id.passwordLayout)
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout)
        progressBar = findViewById(R.id.progressBar)

        auth = FirebaseAuth.getInstance()


        signUpButton.setOnClickListener {
            val name = nameLayout.editText?.text.toString()
            val email = emailLayout.editText?.text.toString()
            val password = passwordLayout.editText?.text.toString()
            val confirmPassword = confirmPasswordLayout.editText?.text.toString()

            if (name.isEmpty() || name.isBlank()) {
                nameLayout.error = "Enter your name"
            } else if (!checkEmail(email)) {
                emailLayout.error = "Enter correct email"
            } else if (!confirmPassword(password,confirmPassword)) {
                confirmPasswordLayout.error = "Passwords do not match"
            } else if (!checkPassword(password)) {
                passwordLayout.error = "Password must have at least 6 characters"
            } else {
                progressBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isComplete && it.isSuccessful) {
                        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
                            progressBar.visibility = View.INVISIBLE
                            if (task.isComplete && task.isSuccessful) {
                                showDialog(email)
                            } else {
                                Toast.makeText(this,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        progressBar.visibility = View.VISIBLE
                        Toast.makeText(this,it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showDialog(email: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Verify Email Address")
        builder.setMessage("We sent an email to $email to make sure you own it.\n" +
                "Please check your inbox to finish setting up your account.\n" +
                "Do check the spam folder.")
        builder.setIcon(R.drawable.ic_mail)
        builder.setPositiveButton("Proceed to Login"){ _, _ ->
            startActivity(Intent(this,LoginActivity::class.java))
        }

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun checkPassword(password: String): Boolean {
        return password.length > 6
    }

    private fun confirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    private fun checkEmail(email: String): Boolean {
        return email.isNotBlank() && email.isNotEmpty() && email.contains('@')
    }
}