package me.xap3y.spitus

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import me.xap3y.spitus.Utils.DataManager
import me.xap3y.spitus.Utils.StorageManager
import me.xap3y.spitus.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataManager: DataManager
    private val appVer = "0.0.1";


    override fun onCreate(savedInstanceState: Bundle?) {

        dataManager = DataManager.getInstance(this)
        dataManager.saveString("homePage", "Dev build - v$appVer")
        dataManager.saveString("appVer", appVer)
        Log.d("dataManager", dataManager.getString("homePage", null))

        val json = StorageManager.createJsonString()
        dataManager.saveString("json", json)

        //recyclerView.adapter = serverAdapter

        super.onCreate(savedInstanceState)

        val parser = StorageManager.parseJsonString(dataManager.getString("json"))

        for (server in parser.servers) {
            Log.d("LOOP Main.kt:42", "Server name: ${server.name}, ADDRes: ${server.address}, PORT: ${server.port}")
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_servers, R.id.nav_settings, R.id.nav_about, R.id.nav_developer
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}