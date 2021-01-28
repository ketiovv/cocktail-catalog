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
import com.example.cocktailcatalog.models.entities.LocalDrinkNameAndIngredients
import com.example.cocktailcatalog.viewmodels.DrinkViewModel
import kotlinx.android.synthetic.main.fragment_add_drink_details.*

private const val ARG_FIRST_PAGE_DATA = "firstPageData"

class AddDrinkDetailsFragment : Fragment() {
    private lateinit var firstPageData :LocalDrinkNameAndIngredients

    private lateinit var drinkViewModel:DrinkViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstPageData = it.getParcelable(ARG_FIRST_PAGE_DATA)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        drinkViewModel = ViewModelProvider(requireActivity()).get(DrinkViewModel::class.java)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drink_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // testowo..
        Log.d("test",firstPageData.name)
        Log.d("test","ingredient list: ")
        for (x in firstPageData.ingredients){
            Log.d("test", x.name)
        }

        val instructions = editTextNewDrinkInstructions.text

        buttonAddDrinkToLocalDatabase.setOnClickListener {
            if (instructions.isNullOrBlank()){
                editTextNewDrinkInstructions.error = "Instrutions can't be empty!"
            }// TODO: walidacja na miary
            else{
                // add drink to database
                drinkViewModel.addDrinkToLocalDatabase(
                    firstPageData.name,
                    "test",
                    instructions.toString(),
                    "test",
                    "test",
                    "test")

                // TODO: add ingredients to db

                // TODO: add relations to db

                // TODO: later maybe it should navigate to view of this drink
                view.findNavController().navigate(R.id.action_addDrinkDetailsFragment_to_addDrinkFragment)
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