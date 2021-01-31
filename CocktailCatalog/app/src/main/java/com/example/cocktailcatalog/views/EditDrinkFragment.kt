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
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.adapters.IngredientListInEditPageAdapter
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import kotlinx.android.synthetic.main.fragment_edit_drink.*

class EditDrinkFragment : Fragment() {

    private lateinit var drinkViewModel: DrinkViewModel
    private lateinit var ingredientViewModel: IngredientViewModel

    private lateinit var ingredientListAdapter: IngredientListInEditPageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        ingredientViewModel = ViewModelProvider(requireActivity()).get(IngredientViewModel::class.java)
        drinkViewModel = ViewModelProvider(requireActivity()).get(DrinkViewModel::class.java)

        ingredientListAdapter = IngredientListInEditPageAdapter(ingredientViewModel.drinkIngredients){
            ingredientViewModel.deleteIngredientFromLocalDb(it)
        }

        viewManager = LinearLayoutManager(context)

        ingredientViewModel.drinkIngredients.observe(viewLifecycleOwner){
            ingredientListAdapter.notifyDataSetChanged()
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_drink, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewIngredientsInDrink.apply {
            adapter = ingredientListAdapter
            layoutManager = viewManager
        }

        initFields()
    }

    private fun initFields(){
        val drinkName = editTextEditDrinkName
        val instructions = editTextEditDrinkInstructions
        drinkName.setText(DrinkViewModel.selectedLocalDrink.name)
        instructions.setText(DrinkViewModel.selectedLocalDrink.instructions)
        editTextEditDrinkImageURL.setText(DrinkViewModel.selectedLocalDrink.image)
        switchEditDrinkAlcoholic.isChecked = DrinkViewModel.selectedLocalDrink.alcoholic
        buttonSaveEditedFields.setOnClickListener {
            if (drinkName.text.isNullOrBlank()){
                drinkName.error = "Name can't be empty!"
            }
            else if(instructions.text.isNullOrBlank()){
                instructions.error = "Name can't be empty!"
            }
            else{
                drinkViewModel.updateDrinkInLocalDb(
                    DrinkViewModel.selectedLocalDrink,
                    drinkName.text.toString(),
                    instructions.text.toString(),
                    editTextEditDrinkImageURL.text.toString(),
                    switchEditDrinkAlcoholic.isChecked
                )
                it.findNavController().navigate(R.id.action_editDrinkFragment_to_favoriteDrinksFragment)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = EditDrinkFragment()

    }
}