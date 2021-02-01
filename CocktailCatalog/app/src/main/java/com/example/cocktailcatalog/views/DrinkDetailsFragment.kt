package com.example.cocktailcatalog.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_drink_details.*
import kotlinx.coroutines.launch

class DrinkDetailsFragment : Fragment() {

    private lateinit var viewModel: DrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        lifecycleScope.launch {
            viewModel.addDrinkToHistory(DrinkViewModel.selectedDrink)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewDrinkNameD.text = DrinkViewModel.selectedDrink.name
        textViewInstructions.text = DrinkViewModel.selectedDrink.instructions

        Picasso.get().load(DrinkViewModel.selectedDrink.image).resize(700, 700)
                .centerCrop()
                .into(imageView)

        val ingredients = DrinkViewModel.selectedDrink.ingredients

        for (i in ingredients){
            if(i.measure != "Some")
                textViewIngredientsList.text = textViewIngredientsList.text.toString() + "${i.name}  ${i.measure} \n"
            else
                textViewIngredientsList.text = textViewIngredientsList.text.toString() + "${i.measure} ${i.name} \n"
        }

        lifecycleScope.launch {
            if (viewModel.checkIfDrinkIsFavourite(DrinkViewModel.selectedDrink)) {
                buttonAddToFavorites.setImageResource(R.drawable.ic_baseline_favorite_24_kivi)
                DrinkViewModel.selectedDrink.favorite = true
                textViewAddToFavorites.text = getString(R.string.remove_from_favorite)
            } else {
                buttonAddToFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24_kici)
                DrinkViewModel.selectedDrink.favorite = false
                textViewAddToFavorites.text = getString(R.string.add_to_favorite)
            }
        }


        buttonAddToFavorites.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.checkIfDrinkIsFavourite(DrinkViewModel.selectedDrink)) {
                    buttonAddToFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24_kici)
                    DrinkViewModel.selectedDrink.favorite = false
                    textViewAddToFavorites.text = getString(R.string.add_to_favorite)
                    viewModel.deleteDrinkFromFavourites(DrinkViewModel.selectedDrink)

                } else if (!viewModel.checkIfDrinkIsFavourite(DrinkViewModel.selectedDrink)) {
                    buttonAddToFavorites.setImageResource(R.drawable.ic_baseline_favorite_24_kivi)
                    DrinkViewModel.selectedDrink.favorite = true
                    textViewAddToFavorites.text = getString(R.string.remove_from_favorite)
                    viewModel.addDrinkToFavourite(DrinkViewModel.selectedDrink)
                }
            }
        }

        buttonBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_drinkDetailsFragment_to_searchFragment)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = DrinkDetailsFragment()
    }
}