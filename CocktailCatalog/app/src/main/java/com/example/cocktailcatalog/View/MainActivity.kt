package com.example.cocktailcatalog.View

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cocktailcatalog.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.outlineAmbientShadowColor = R.color.white

    }
}