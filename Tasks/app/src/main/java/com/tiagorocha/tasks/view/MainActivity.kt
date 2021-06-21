package com.tiagorocha.tasks.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.*
import com.tiagorocha.tasks.R

import com.tiagorocha.tasks.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var _viewModel : MainActivityViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, TaskFormActivity::class.java))
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.all_Task, R.id.nav_next_tasks, R.id.nav_expired, R.id.nav_logout), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            if(it.itemId == R.id.nav_logout){
                 _viewModel.logout()

            }else{
                NavigationUI.onNavDestinationSelected(it, navController)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            true
        }

        observer()
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        _viewModel.loadUserName()
    }
    fun observer() {
        _viewModel.userName.observe(this, Observer {
            val nav = findViewById<NavigationView>(R.id.nav_view)
            val header = nav.getHeaderView(0)
            header.findViewById<TextView>(R.id.textViewName).text = it
        })
        _viewModel.logout.observe(this, Observer {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
    }
}