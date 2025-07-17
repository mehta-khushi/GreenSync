package com.example.greensync

import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.greensync.databinding.ActivityMainBinding
import com.example.greensync.viewmodels.ApplianceViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
        window.statusBarColor = typedValue.data


        val navBarColorValue = TypedValue()
        theme.resolveAttribute(android.R.attr.navigationBarColor, navBarColorValue, true)
        window.navigationBarColor = navBarColorValue.data




        // 1. Initialize Navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 2. Configure AppBar with both drawer and bottom nav
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_dashboard,
                R.id.nav_updates,
                R.id.nav_tips,
                R.id.nav_settings
            ),
            binding.drawerLayout
        )

        // 3. Setup Toolbar and Navigation
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 4. Connect Bottom Navigation
        binding.bottomNav.setupWithNavController(navController)

        // 5. Connect Navigation Drawer
        binding.navView.setupWithNavController(navController)

        binding.bottomNav.selectedItemId = R.id.nav_home
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}