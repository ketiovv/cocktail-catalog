package com.example.cocktailcatalog.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: DrinkViewModel
    private lateinit var sharedPref: SharedPreferences

    //@RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        if (!sharedPref.contains(getString(R.string.over_18))) {
            bottomNavigationView.visibility = View.GONE
            navController.navigate(R.id.ageCheckFragment)
        }

        //bottomNavigationView.outlineAmbientShadowColor = R.color.white

        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        viewModel.getAllDrinks()
    }
}