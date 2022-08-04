package com.codingurkan.photoapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.NavigationUI
import com.codingurkan.photoapp.R
import com.codingurkan.photoapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        bottomNavigationView()
    }
    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
    private fun bottomNavigationView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHost
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.photoSearchFragment, R.id.photoListFragment -> {
                    binding?.bottomNavigationView?.visibility = View.VISIBLE
                }
                else -> {
                    binding?.bottomNavigationView?.visibility = View.GONE
                }
            }
        }
        binding?.let {
            bottomNavigationView = it.bottomNavigationView
            NavigationUI.setupWithNavController(bottomNavigationView, navController)
        }
    }
}