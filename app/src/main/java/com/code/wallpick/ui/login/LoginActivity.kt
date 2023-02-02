package com.code.wallpick.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.code.wallpick.R
import com.code.wallpick.data.auth.AuthRepositoryImpl
import com.code.wallpick.ui.home.HomeActivity
import com.code.wallpick.viewmodel.AuthViewModel
import com.code.wallpick.viewmodel.utils.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signUpButton: TextView
    private lateinit var loginButton: Button
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var googleSignIn: ImageView
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepositoryImpl(FirebaseAuth.getInstance()))
    }

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        googleSignIn = findViewById(R.id.googleLoginButton)
        progressBar = findViewById(R.id.progressBar)
        emailLayout = findViewById(R.id.emailLayout)
        passwordLayout = findViewById(R.id.passwordLayout)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signUpTextView)
        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        loginButton.setOnClickListener {
            initLogin()
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResult(task)
            }
        }

        googleSignIn.setOnClickListener{
            Log.d ("Login","Google button working")
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Req_Code) {
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleResult(task)
//        }
//    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Log.d("LoginActivity",e.toString())
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initLogin() {
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
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    progressBar.visibility = View.INVISIBLE
                    if (it.isComplete && it.isSuccessful) {
                        if (auth.currentUser!!.isEmailVerified) {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                            clearScreen()
                        } else {
                            emailLayout.error = "Email is not verified"
                        }
                    } else {
                        when (it.exception) {
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
        return email.isNotBlank() && email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }
}