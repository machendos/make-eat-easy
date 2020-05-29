package com.example.make_eat_easy.views

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.make_eat_easy.R
import com.example.make_eat_easy.repository.Authenticator
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = host.navController

        val sideBar = findViewById<NavigationView>(R.id.nav_view)
        sideBar.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, findViewById(R.id.drawer_layout))
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolBar.setupWithNavController(navController, appBarConfiguration)

        val s = sideBar.getHeaderView(0)
        s.findViewById<TextView>(R.id.textView).text = Authenticator.getEmail()

    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }


//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
//
    override fun onNavigationItemSelected(item: MenuItem) = true


}
