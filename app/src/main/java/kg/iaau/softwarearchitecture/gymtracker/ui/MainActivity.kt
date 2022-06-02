package kg.iaau.softwarearchitecture.gymtracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.databinding.ActivityMainBinding
import kg.iaau.softwarearchitecture.gymtracker.utils.TestDataObj

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.insertNews(TestDataObj.createNews())
        vm.insertTrainingSections(TestDataObj.createTrainingCategories())
        vm.insertTrainings(TestDataObj.createTrainings())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController
        binding.bottomNavView.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener {
                // Do nothing to ignore the reselection
            }
        }
    }
}