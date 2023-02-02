package com.code.wallpick.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.MenuItem
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.code.wallpick.App
import com.code.wallpick.R
import com.code.wallpick.adapter.HomeViewPagerAdapter
import com.code.wallpick.data.remote.RetrofitHelper
import com.code.wallpick.data.remote.WallpapersService
import com.code.wallpick.data.remote.WallpaperRepositoryImpl
import com.code.wallpick.service.ShakeService
import com.code.wallpick.ui.login.LoginActivity
import com.code.wallpick.viewmodel.HomeViewModel
import com.code.wallpick.viewmodel.utils.HomeViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.todo.shakeit.core.ShakeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), ShakeListener {

    private lateinit var toolbar: Toolbar

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var trending: FrameLayout
    private lateinit var playlist: FrameLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2



    private lateinit var trendingText: TextView
    private lateinit var playlistText: TextView
    private lateinit var trendingIcon: ImageView
    private lateinit var playlistIcon: ImageView

    val viewModel: HomeViewModel by viewModels()
    lateinit var selectorFragment: Fragment
    lateinit var auth: FirebaseAuth

    lateinit var nameText: TextView
    lateinit var emailText: TextView

    private lateinit var sharedPrefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPrefs  = getSharedPreferences(App.PREFERENCES, MODE_PRIVATE)


        window.statusBarColor = getColor(R.color.dark_blue)

        initTabLayout()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        viewModel.initTrendingWallpapers()


        initNavLayout()

        val headerView = navigationView.getHeaderView(0)
        nameText = headerView.findViewById(R.id.name_text)
        emailText = headerView.findViewById(R.id.email_text)
        auth = FirebaseAuth.getInstance()
        nameText.text = auth.currentUser!!.displayName
        emailText.text = auth.currentUser!!.email
        Log.d("HomeScreen",auth.currentUser.toString())

        initViewPager()


    }

    private fun initNavLayout() {
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        if (sharedPrefs.getBoolean(App.SHAKE_SERVICE,false)) {
            navigationView.setCheckedItem(R.id.shake_wallpaper)
        }

        setNavListener()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.home_view_pager)
        viewPager.adapter = HomeViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) {
                tab.setIcon(R.drawable.ic_trending)
            } else if (position == 1) {
                tab.setIcon(R.drawable.ic_playlist)
            }
        }.attach()
    }

    private fun initTabLayout() {
        tabLayout = findViewById(R.id.tabLayout)
    }

    private fun setNavListener() {
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    auth.signOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
                R.id.shake_wallpaper -> {
                    if (item.isChecked) {
                        item.isChecked = false
                        val editor = sharedPrefs.edit()
                        editor.putBoolean(App.SHAKE_SERVICE, false)
                        editor.apply()
                        unregisterService()
                    } else {
                        item.isChecked = true
                        registerService()
                        val editor = sharedPrefs.edit()
                        editor.putBoolean(App.SHAKE_SERVICE, true)
                        editor.apply()
                    }
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun unregisterService() {
        val serviceIntent = Intent(this, ShakeService::class.java)
        stopService(serviceIntent)
    }

    private fun registerService() {
        //registerReceiver(ScreenLockReceiver(), IntentFilter(Intent.ACTION_USER_PRESENT))
        val serviceIntent = Intent(this, ShakeService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
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