package com.ciastek.library.newlayout

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import com.ciastek.library.R
import kotlinx.android.synthetic.main.app_bar_main2.toolbar
import kotlinx.android.synthetic.main.activity_main2.drawer_layout as drawer
import kotlinx.android.synthetic.main.activity_main2.nav_view as navView

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.main_nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_my_library, R.id.nav_library, R.id.nav_settings), drawer)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
