package com.example.cocktailcatalog.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.adapters.DrinkListAdapter
import com.example.cocktailcatalog.adapters.LocalDrinkListAdapter
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import kotlinx.android.synthetic.main.fragment_favourite_own_drinks.*
import kotlinx.android.synthetic.main.fragment_search_drink.*
import kotlinx.android.synthetic.main.fragment_search_drink.recyclerViewDrinkListSearch

class FavouriteOwnDrinksFragment : Fragment() {
    private lateinit var drinkViewModel: DrinkViewModel
    private lateinit var drinkListAdapter: LocalDrinkListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drinkViewModel = ViewModelProvider(requireActivity()).get(DrinkViewModel::class.java)

        drinkListAdapter = LocalDrinkListAdapter( drinkViewModel.allLocalDrinks){
            Log.d("test", it.name)
            //view?.findNavController()?.navigate(R.id.action_searchFragment_to_drinkDetailsFragment)
        }

        viewManager = LinearLayoutManager(requireContext())

        drinkViewModel.allLocalDrinks.observe(viewLifecycleOwner){
            drinkListAdapter.notifyDataSetChanged()
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_own_drinks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewLocalDrinkList.apply{
            adapter = drinkListAdapter
            layoutManager=viewManager
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteOwnDrinksFragment()
    }
}