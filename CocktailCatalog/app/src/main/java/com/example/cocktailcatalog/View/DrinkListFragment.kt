package com.example.cocktailcatalog.View

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cocktailcatalog.ViewModel.DrinkListViewModel
import com.example.cocktailcatalog.R
import kotlinx.android.synthetic.main.drink_list_fragment.*

class DrinkListFragment : Fragment() {

    companion object {
        fun newInstance() = DrinkListFragment()
    }

    private lateinit var viewModel: DrinkListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DrinkListViewModel::class.java)


        return inflater.inflate(R.layout.drink_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            viewModel.getDrinksByName("margarita")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}