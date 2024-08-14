package com.example.galleryapp.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.galleryapp.R
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.network.ConnectivityObserver
import com.example.network.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding



    //Проверка интернета
    private lateinit var networkBlank: ConstraintLayout

    private lateinit var connectivityObserver: ConnectivityObserver



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        binding = ActivityMainBinding.inflate(layoutInflater)

//        Получение зависимостей



        setContentView(binding.root)

        networkBlank = findViewById(R.id.network_include)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //navigation code
        val btnNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        val controller = findNavController(R.id.fragmentContainerView)
        btnNavView.setupWithNavController(controller)
        //navigation code




        //Проверка интернета после запуска
        val currentStatus =  (connectivityObserver as NetworkConnectivityObserver)  .getCurrentStatus()
        networkBlank.visibility = if(currentStatus == ConnectivityObserver.Status.Available){
            ConstraintLayout.GONE
        }
        else{
            ConstraintLayout.VISIBLE
        }

        //Ассинхронная проверка интернета
        lifecycleScope.launch {
            connectivityObserver.observe().collect { status ->
                when(status){
                    ConnectivityObserver.Status.Available -> {
                        networkBlank.visibility = ConstraintLayout.GONE
                    }
                    else -> {
                        networkBlank.visibility = ConstraintLayout.VISIBLE
                    }
                }

            }
        }




        //Навигационное названия на страницах
        setSupportActionBar(binding.customToolBar)

        setupActionBarWithNavController(
            controller,
            AppBarConfiguration(setOf(R.id.newFragment, R.id.popularFragment))
        )
        binding.customToolBar.setNavigationOnClickListener {
            controller.popBackStack()
        }
        //Навигационное названия на страницах




    }

}