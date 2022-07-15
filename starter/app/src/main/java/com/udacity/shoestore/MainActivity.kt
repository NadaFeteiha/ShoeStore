package com.udacity.shoestore

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.ui.ShoeListFragmentDirections
import com.udacity.shoestore.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        init()
        setListener()
    }

    private fun init() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.shoe_nav)

        val status = Util.getLoginStatus(this)
        navGraph.startDestination = if (status) {
            R.id.shoeListFragment
        } else {
            R.id.loginFragment
        }
        navController.graph = navGraph
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    private fun setListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.signUpFragment -> {
                    binding.toolbar.visibility = View.GONE
                }
                R.id.welcomeFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.title = resources.getString(R.string.welcome)
                }
                R.id.instructionsFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.title = resources.getString(R.string.instructions)
                }
                R.id.shoeDetailFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.title = resources.getString(R.string.add_new_shoe_title)
                }
                else -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.title = resources.getString(R.string.app_name)
                }
            }
        }
    }
}
