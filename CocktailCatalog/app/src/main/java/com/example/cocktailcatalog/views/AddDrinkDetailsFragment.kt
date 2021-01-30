package com.example.cocktailcatalog.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.adapters.IngredientMeasureAdapter
import com.example.cocktailcatalog.models.entities.LocalDrinkNameAndIngredients
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import kotlinx.android.synthetic.main.fragment_add_drink_details.*
import kotlinx.coroutines.launch

private const val ARG_FIRST_PAGE_DATA = "firstPageData"

class AddDrinkDetailsFragment : Fragment() {
    private lateinit var firstPageData :LocalDrinkNameAndIngredients

    private lateinit var drinkViewModel:DrinkViewModel
    private lateinit var ingredientViewModel: IngredientViewModel


    private lateinit var ingredientMeasureAdapter: IngredientMeasureAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstPageData = it.getParcelable(ARG_FIRST_PAGE_DATA)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        drinkViewModel = ViewModelProvider(requireActivity()).get(DrinkViewModel::class.java)
        ingredientViewModel = ViewModelProvider(requireActivity()).get(IngredientViewModel::class.java)

        ingredientMeasureAdapter = IngredientMeasureAdapter(firstPageData.ingredients)
        viewManager = LinearLayoutManager(requireContext())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drink_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewIngredientsMeasureEntering.apply{
            adapter = ingredientMeasureAdapter
            layoutManager = viewManager
        }

        val instructions = editTextNewDrinkInstructions.text
        val imageUrl = editTextImageURL.text
        val alcoholic = switchAlcoholic.isChecked

        buttonAddDrinkToLocalDatabase.setOnClickListener {
            if (instructions.isNullOrBlank()){
                editTextNewDrinkInstructions.error = "Instrutions can't be empty!"
            }// TODO: walidacja na miary
            else {
                lifecycleScope.launch{
                    val drinkId = drinkViewModel.addDrinkToLocalDatabase(
                        firstPageData.name, instructions.toString(),imageUrl.toString(), alcoholic)

                    for (ingredient in ingredientMeasureAdapter.ingredients){
                        val ingredientId = ingredientViewModel.addIngredientToLocalDatabase(
                            ingredient.name, ingredient.measure, drinkId)
                    }
                }

                view.findNavController()
                    .navigate(R.id.action_addDrinkDetailsFragment_to_addDrinkFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(firstPageData: LocalDrinkNameAndIngredients) =
                AddDrinkDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_FIRST_PAGE_DATA, firstPageData)
                    }
                }
    }
}