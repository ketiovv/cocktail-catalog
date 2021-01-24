package com.example.cocktailcatalog.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.adapters.DrinkListAdapter
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import kotlinx.android.synthetic.main.fragment_search_drink.*

class SearchByNameFragment : Fragment() {

    private lateinit var viewModel: DrinkViewModel
    private lateinit var drinkListAdapter: DrinkListAdapter
    private lateinit var ingredientViewModel: IngredientViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        viewManager = LinearLayoutManager(requireContext())
        drinkListAdapter = DrinkListAdapter(viewModel.listOfDrinks)
        viewModel.getDrinksByName("margarita")


        viewModel.listOfDrinks.observe(viewLifecycleOwner, {
            drinkListAdapter.notifyDataSetChanged()
        })

        return inflater.inflate(R.layout.fragment_search_drink, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewDrinkListSearch.apply{
            adapter = drinkListAdapter
            layoutManager=viewManager
        }

        searchViewDrink.setOnSearchClickListener{
            Log.d("myTag","Chuj")
        }

        searchViewDrink.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query != null){
//                    viewModel.getDrinksByName(query)
//                    return true
//                }
//                else return false
                searchViewDrink.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    viewModel.getDrinksByName(newText)
                    return true
                }
                else return false
            }
        })

    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchByNameFragment()
    }


}