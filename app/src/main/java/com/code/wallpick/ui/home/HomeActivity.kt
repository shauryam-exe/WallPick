package com.code.wallpick.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.R
import com.code.wallpick.api.RetrofitHelper
import com.code.wallpick.api.WallpapersService
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.ui.login.LoginActivity
import com.code.wallpick.viewmodel.HomeViewModel
import com.code.wallpick.viewmodel.utils.HomeViewModelFactory
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.pedromassango.doubleclick.DoubleClick
import com.pedromassango.doubleclick.DoubleClickListener
import com.todo.shakeit.core.ShakeDetector
import com.todo.shakeit.core.ShakeIt
import com.todo.shakeit.core.ShakeListener

class HomeActivity : AppCompatActivity(), ShakeListener {

    private lateinit var toolbar: Toolbar

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var trending: LinearLayout
    private lateinit var playlist: LinearLayout

    lateinit var viewModel: HomeViewModel
    lateinit var selectorFragment: Fragment
    lateinit var auth: FirebaseAuth

    lateinit var nameText: TextView
    lateinit var emailText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        trending = findViewById(R.id.trending)
        playlist = findViewById(R.id.playlist)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        val wallpapersService = RetrofitHelper.getInstance().create(WallpapersService::class.java)
        val repo = WallpaperRepository(wallpapersService)
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)
        viewModel.initTrendingWallpapers()


        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setNavListener()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val headerView = navigationView.getHeaderView(0)
        nameText = headerView.findViewById(R.id.name_text)
        emailText = headerView.findViewById(R.id.email_text)
        auth = FirebaseAuth.getInstance()
        nameText.text = auth.currentUser!!.displayName
        emailText.text = auth.currentUser!!.email


        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TrendingFragment())
            .addToBackStack(null).commit()

        trending.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,TrendingFragment()).commit()
            findViewById<View>(R.id.playlist_line).visibility = View.INVISIBLE
            findViewById<View>(R.id.trending_line).visibility = View.VISIBLE
        }

        playlist.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlaylistsFragment())
                .addToBackStack(null).commit()
            findViewById<View>(R.id.playlist_line).visibility = View.VISIBLE
            findViewById<View>(R.id.trending_line).visibility = View.INVISIBLE

        }



    }

    private fun setNavListener() {
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    auth.signOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            Log.d("shauryam", "$item item selected")
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onShake() {
        Toast.makeText(this,"Shake Detected", Toast.LENGTH_SHORT).show()
        Log.d("Shake","Shake Detected")
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.home_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
}