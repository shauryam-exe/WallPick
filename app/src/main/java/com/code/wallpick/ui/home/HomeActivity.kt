package com.code.wallpick.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.R
import com.code.wallpick.api.RetrofitHelper
import com.code.wallpick.api.WallpapersService
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.viewmodel.HomeViewModel
import com.code.wallpick.viewmodel.utils.HomeViewModelFactory
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var trending: LinearLayout
    private lateinit var playlist: TextView

    lateinit var viewModel: HomeViewModel
    lateinit var selectorFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        trending = findViewById(R.id.trending)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Test Action Bar"

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setNavListener()

//        val wallpapersService = RetrofitHelper.getInstance().create(WallpapersService::class.java)
//        val repo = WallpaperRepository(wallpapersService)
//        viewModel =
//            ViewModelProvider(this, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        trending = findViewById(R.id.trending)
        trending.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TrendingFragment()).commit()
        }

//        viewModel.wallpapers.observe(this) {
//            Log.d("pixel", it.photos[0].url)
//        }

    }

    private fun setNavListener() {
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_account -> Toast.makeText(
                    this@HomeActivity,
                    "My Account Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_logout -> {
                    Toast.makeText(
                        this@HomeActivity,
                        "Logout Clicked",
                        Toast.LENGTH_SHORT
                    ).show()

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}