package me.xap3y.spitus

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import me.xap3y.spitus.Utils.DataManager
import me.xap3y.spitus.Utils.Logger.Companion.DEBUG
import me.xap3y.spitus.Utils.Logger.Companion.ERROR
import me.xap3y.spitus.Utils.Logger.Companion.logger
import me.xap3y.spitus.Utils.StorageManager
import me.xap3y.spitus.databinding.ActivityMainBinding
import me.xap3y.spitus.ui.servers.ServersFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataManager: DataManager
    private lateinit var navController: NavController
    private val appVer = "0.0.1"


    override fun onCreate(savedInstanceState: Bundle?) {


        dataManager = DataManager.getInstance(this)
        //dataManager.saveString("json", StorageManager.createDefaultJsonString())
        if(dataManager.getString("env_firstTimeLaunch", "null") == "null") {
            logger(DEBUG, "DataManager", "App launched for first time, creating default servers JSON.")
            try {
                dataManager.saveString("json", StorageManager.createDefaultJsonString())
                dataManager.saveString("env_firstTimeLaunch", "now")
                logger(DEBUG, "DataManager", "Default servers JSON saved.")
            } catch (e: Exception) {
                logger(ERROR, "DataManager", "Cannot create default servers JSON!", e.toString())
            }
        }

        try {
            logger(DEBUG, "DataManager", "Saving appVer $appVer")
            dataManager.saveString("appVer", appVer)
            logger(DEBUG, "DataManager", "Saved appVer")
        } catch (e: Exception) {
            logger(ERROR, "DataManager", "Cannot save appVer!", e.toString())
        }

        //val json = StorageManager.createJsonString()
        //dataManager.saveString("json", json)

        //recyclerView.adapter = serverAdapter

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_servers, R.id.nav_settings, R.id.nav_about, R.id.nav_developer
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val yourButton = findViewById<FloatingActionButton>(R.id.fab)
        yourButton.setOnClickListener {
            navigateToServerFragment()
        }
    }

    private fun navigateToServerFragment() {
        try {
            logger(DEBUG, "NavController", "Setting controller and navigating to nav_developer...")
            val serverFragment = ServersFragment()
            serverFragment.setNavController(navController)
            navController.navigate(R.id.nav_developer)
            logger(DEBUG, "NavController", "Navigated to nav_developer.")
        } catch (e: Exception) {
            logger(ERROR, "navController", "Cannot navigate to server fragment!", e.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}