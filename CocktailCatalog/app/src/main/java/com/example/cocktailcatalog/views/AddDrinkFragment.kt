package com.example.cocktailcatalog.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.adapters.IngredientListAdapter
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.models.entities.LocalDrinkNameAndIngredients
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import kotlinx.android.synthetic.main.fragment_add_drink.*
import kotlinx.android.synthetic.main.fragment_search_by_ingredient.*

class AddDrinkFragment : Fragment() {

    private lateinit var ingredientViewModel: IngredientViewModel

    private lateinit var ingredientListAdapter: IngredientListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ingredientViewModel = ViewModelProvider(requireActivity()).get(IngredientViewModel::class.java)
        ingredientViewModel.getIngredientNameList()

        ingredientListAdapter = IngredientListAdapter(ingredientViewModel.listOfIngredientNames )

        viewManager = LinearLayoutManager(requireContext())


        ingredientViewModel.listOfIngredientNames.observe(viewLifecycleOwner, {
            ingredientListAdapter.ingredientsUnFiltered = IngredientViewModel.unFilterIngredients.value
            ingredientListAdapter.notifyDataSetChanged()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drink, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewIngredientListToCheck.apply{
            adapter = ingredientListAdapter
            layoutManager = viewManager
        }

        val name = editTextNewDrinkDescription.text;
        buttonMoveToNextPage.setOnClickListener {
            if (name.toString().isNullOrBlank()){
                editTextNewDrinkDescription.error = "Name can't be empty!"
            }
            else if(ingredientListAdapter.getSelectedItemsCount() < 1){
                //TODO: How to display? Toast?
            }
            else{
                val selectedIngredients = ArrayList<Ingredient>()
                for (x in ingredientListAdapter.selectedIngredient){
                    selectedIngredients.add(Ingredient(0,x,"",0))
                }
                val firstPageData = LocalDrinkNameAndIngredients(name.toString(), selectedIngredients)
                view.findNavController().navigate(
                    R.id.action_addDrinkFragment_to_addDrinkDetailsFragment,
                    bundleOf("firstPageData" to firstPageData)
                )
            }
        }

        searchViewAddIngredient.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewIngredient.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ingredientListAdapter.filter.filter(newText)
                return true
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = AddDrinkFragment()

    }
}