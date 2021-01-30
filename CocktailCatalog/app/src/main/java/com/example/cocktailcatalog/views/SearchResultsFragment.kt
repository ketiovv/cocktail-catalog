package com.example.cocktailcatalog.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.adapters.DrinkListAdapter
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import kotlinx.android.synthetic.main.fragment_search_drink.*
import kotlinx.android.synthetic.main.fragment_search_results.*

class SearchResultsFragment : Fragment() {

    private lateinit var viewModel: DrinkViewModel
    private lateinit var drinkListAdapter: DrinkListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        viewManager = LinearLayoutManager(requireContext())
        drinkListAdapter = DrinkListAdapter(viewModel.listOfDrinks){ it ->
            viewModel.getDrinksById(it.id){ d ->
                DrinkViewModel.selectedDrink = d
                view?.findNavController()?.navigate(R.id.action_searchResultsFragment_to_drinkDetailsFragment)
            }

        }
        viewModel.getDrinksByIngrediends(IngredientViewModel.selectedIngredients)


        viewModel.listOfDrinks.observe(viewLifecycleOwner, {
            drinkListAdapter.notifyDataSetChanged()
        })

        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewResults.apply{
            adapter = drinkListAdapter
            layoutManager=viewManager
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchResultsFragment()
    }
}