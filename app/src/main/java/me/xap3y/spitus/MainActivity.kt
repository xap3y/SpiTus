package me.xap3y.spitus

import android.os.Bundle
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
import me.xap3y.spitus.Utils.SafeCallBack
import me.xap3y.spitus.Utils.strucs.CallBackResult
import me.xap3y.spitus.databinding.ActivityMainBinding
import me.xap3y.spitus.events.onCreate.Companion.firstTimeLaunch
import me.xap3y.spitus.ui.servers.ServersFragment


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataManager: DataManager
    private lateinit var navController: NavController
    private lateinit var callRes: CallBackResult
    private val appVer = "0.0.1"


    override fun onCreate(savedInstanceState: Bundle?) {

        //dataManager = DataManager.getInstance(this) //No safe call
        callRes = SafeCallBack.Callback { dataManager = DataManager.getInstance(this) }
        if(!callRes.success) {
            logger(ERROR, "DataManager", callRes.errorMessage!!)
            finish()
        }
        else {
            logger(DEBUG, "DataManager", "DataManager initialized. in ${callRes.runTime}ms")
        }


        //dataManager.saveString("json", StorageManager.createDefaultJsonString())


        if(firstTimeLaunch(dataManager)) finish()


        if( SafeCallBack.Callback { dataManager.saveString("appVer", appVer) }.success ) {
            logger(DEBUG, "DataManager", "App version saved. in ${callRes.runTime}ms")
        }
        else {
            logger(ERROR, "DataManager", "Cannot save app version! ERR: ${callRes.errorMessage}")
            //finish()
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