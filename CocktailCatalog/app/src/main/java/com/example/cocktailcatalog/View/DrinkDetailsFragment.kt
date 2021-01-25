package com.example.cocktailcatalog.View

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.ViewModel.DrinkViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_drink_details.*

class DrinkDetailsFragment : Fragment() {

    private lateinit var viewModel: DrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)


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


        buttonAddToFavorites.setOnClickListener {
            //TODO: Change to list contains,
            if(DrinkViewModel.selectedDrink.favorite){
                buttonAddToFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24_kici)
                DrinkViewModel.selectedDrink.favorite = false
                textViewAddToFavorites.text = getString(R.string.add_to_favorite )
            }
            else if(!DrinkViewModel.selectedDrink.favorite){
                buttonAddToFavorites.setImageResource(R.drawable.ic_baseline_favorite_24_kivi)
                DrinkViewModel.selectedDrink.favorite = true
                textViewAddToFavorites.text = getString(R.string.remove_from_favorite)
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