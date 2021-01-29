package com.example.cocktailcatalog.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_drink_details.*
import kotlinx.android.synthetic.main.fragment_local_drink_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocalDrinkDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocalDrinkDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var ingredientViewModel: IngredientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
            Log.d("test", ingredient.name)
            textViewIngredientsListF.text = textViewIngredientsListF.text.toString() + "${ingredient.measure} ${ingredient.name} \n"
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
        //TODO: dam musisz dodać do tego picassa żeby jak nie ma linku to imageview.visibility  = View.gone


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LocalDrinkDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LocalDrinkDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}