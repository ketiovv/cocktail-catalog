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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditDrinkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditDrinkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var drinkViewModel: DrinkViewModel
    private lateinit var ingredientViewModel: IngredientViewModel

    private lateinit var ingredientListAdapter: IngredientListInEditPageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditDrinkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EditDrinkFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}