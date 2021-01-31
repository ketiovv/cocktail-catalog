package com.example.cocktailcatalog.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_drink_details.*
import kotlinx.android.synthetic.main.fragment_local_drink_details.*

class LocalDrinkDetailsFragment : Fragment() {

    private lateinit var drinkViewModel: DrinkViewModel
    private lateinit var ingredientViewModel: IngredientViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        drinkViewModel = ViewModelProvider(requireActivity()).get(DrinkViewModel::class.java)
        ingredientViewModel = ViewModelProvider(requireActivity()).get(IngredientViewModel::class.java)

        ingredientViewModel.drinkIngredients.observe(viewLifecycleOwner){
            updateIngredients(it)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_drink_details, container, false)
    }

    private fun updateIngredients(list: List<Ingredient>) {
        textViewIngredientsListF.text = ""
        for (ingredient in list){
            textViewIngredientsListF.text = textViewIngredientsListF.text.toString() + "${ingredient.name} ${ingredient.measure}\n"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewDrinkNameDF.text = DrinkViewModel.selectedLocalDrink.name
        textViewInstructionsF.text = DrinkViewModel.selectedLocalDrink.instructions

        if (DrinkViewModel.selectedLocalDrink.image.isNullOrBlank()){
            imageViewF.visibility  = View.GONE
        }
        else{
            Picasso.get().load(DrinkViewModel.selectedLocalDrink.image).resize(700, 700)
                .centerCrop()
                .into(imageViewF)
        }

        buttonBackF.setOnClickListener {
            it.findNavController().navigate(R.id.action_localDrinkDetailsFragment_to_favoriteDrinksFragment)
        }

        buttonEditF.setOnClickListener {
            it.findNavController().navigate(R.id.action_localDrinkDetailsFragment_to_editDrinkFragment)
        }

        buttonDeleteF.setOnClickListener {
            it.findNavController().navigate(R.id.action_localDrinkDetailsFragment_to_favoriteDrinksFragment)
            drinkViewModel.deleteDrinkFromLocalDb(DrinkViewModel.selectedLocalDrink)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LocalDrinkDetailsFragment()
    }
}