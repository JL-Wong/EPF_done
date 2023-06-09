package my.edu.tarc.epf

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.epf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                R.id.nav_home, R.id.nav_dividend, R.id.nav_investment, R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_profile) {
                binding.appBarMain.toolbar.menu.findItem(R.id.action_settings).isVisible = false
                binding.appBarMain.toolbar.menu.findItem(R.id.action_about).isVisible = false
                binding.appBarMain.toolbar.menu.findItem(R.id.action_save).isVisible = true
            } else if (destination.id == R.id.nav_home) {
                binding.appBarMain.toolbar.menu.findItem(R.id.action_settings).isVisible = false
                binding.appBarMain.toolbar.menu.findItem(R.id.action_about).isVisible = false
                binding.appBarMain.toolbar.menu.findItem(R.id.action_save).isVisible = false
            } else {
                binding.appBarMain.toolbar.visibility = View.VISIBLE
            }
        }

        val view = navView.getHeaderView(0) //Refer to the profile picture ImageView
        view.setOnClickListener {
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.nav_profile)
            binding.drawerLayout.closeDrawers()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            Snackbar.make(
                findViewById(R.id.nav_host_fragment_content_main),
                R.string.action_settings,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }
}