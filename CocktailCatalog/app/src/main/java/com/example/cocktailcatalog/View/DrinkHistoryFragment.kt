package com.example.cocktailcatalog.View

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cocktailcatalog.ViewModel.DrinkViewModel
import com.example.cocktailcatalog.R
import kotlinx.android.synthetic.main.drink_history_fragment.*

class DrinkHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = DrinkHistoryFragment()
    }

    private lateinit var viewModel: DrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)


        return inflater.inflate(R.layout.drink_history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}