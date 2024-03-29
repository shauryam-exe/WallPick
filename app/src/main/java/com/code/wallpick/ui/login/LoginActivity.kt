package com.code.wallpick.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.code.wallpick.R
import com.code.wallpick.data.auth.AuthRepositoryImpl
import com.code.wallpick.data.auth.AuthState
import com.code.wallpick.ui.home.HomeActivity
import com.code.wallpick.viewmodel.AuthViewModel
import com.code.wallpick.viewmodel.utils.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var googleSignIn: CardView
    private lateinit var facebookLogIn: CardView

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepositoryImpl(FirebaseAuth.getInstance()))
    }

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        googleSignIn = findViewById(R.id.googleLoginButton)
        facebookLogIn = findViewById(R.id.facebookLoginButton)
        window.statusBarColor = getColor(R.color.dark_blue)
        progressBar = findViewById(R.id.progressBar)

        initLoading()
        initAnonymousLogin()
        initGoogleSignIn()
    }

    private fun initLoading() {
        viewModel.authState.observe(this) {
            when (it) {
                is AuthState.Success -> {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    this@LoginActivity.finish()
                }
                is AuthState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is AuthState.Failure -> {
                   progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@LoginActivity, it.exception.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val TAG = "LoginAdapter"

    private fun initAnonymousLogin() {
        facebookLogIn.setOnClickListener {
            //Will be implemented later
            //viewModel.loginAnonymous()
            Toast.makeText(this, "Error Authenticating with Meta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initGoogleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        mGoogleSignInClient.signOut()

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResult(task)
                }
            }

        googleSignIn.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.login(credential)
            }
        } catch (e: ApiException) {
            Log.d("LoginActivity", e.toString())
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}