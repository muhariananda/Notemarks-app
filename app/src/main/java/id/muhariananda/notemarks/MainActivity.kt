package id.muhariananda.notemarks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import id.muhariananda.notemarks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainer.id) as NavHostFragment
        val navController = navHostFragment.navController

        binding.apply {
            bottomNav.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                bottomNav.visibility = when (destination.id) {
                    R.id.noteListFragment -> View.VISIBLE
                    R.id.todoListFragment -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }
}