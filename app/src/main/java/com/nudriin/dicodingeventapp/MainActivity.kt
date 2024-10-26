package com.nudriin.dicodingeventapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.nudriin.dicodingeventapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SearchBarListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.searchBar.visibility = View.GONE

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_upcoming, R.id.navigation_finished, R.id.navigation_finished
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
        }
    }

    override fun showSearchView() {
        binding.searchBar.visibility = View.VISIBLE
    }

    override fun hideSearchView() {
        binding.searchBar.visibility = View.GONE
    }

    override fun getSearchView(): SearchView = binding.searchView

    override fun getSearchBar(): SearchBar = binding.searchBar

}