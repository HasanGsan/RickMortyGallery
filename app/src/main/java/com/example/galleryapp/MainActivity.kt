package com.example.galleryapp

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.network.ApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var networkBlank: ConstraintLayout


    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkBlank = findViewById(R.id.network_include)

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


        //Проверка интернета
        networkBlank = findViewById(R.id.network_include)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        checkInternetConnection()
        //Проверка интернета

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


    //Функция отображения страницы network_error
    private fun checkInternetConnection(){
        apiService.testConnection().enqueue(object: Callback<Unit> {
            override fun onResponse(p0: Call<Unit>, p1: Response<Unit>) {
                networkBlank.visibility = ConstraintLayout.GONE
            }

            override fun onFailure(p0: Call<Unit>, p1: Throwable) {
                networkBlank.visibility = ConstraintLayout.VISIBLE
            }
        })
    }
}