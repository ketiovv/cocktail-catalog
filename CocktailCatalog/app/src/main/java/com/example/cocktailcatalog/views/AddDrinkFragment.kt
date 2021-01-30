package com.example.cocktailcatalog.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddDrinkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddDrinkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var ingredientViewModel: IngredientViewModel

    private lateinit var ingredientListAdapter: IngredientListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ingredientViewModel = ViewModelProvider(requireActivity()).get(IngredientViewModel::class.java)
        ingredientViewModel.getIngredientNameList()

        ingredientListAdapter = IngredientListAdapter(ingredientViewModel.listOfIngredientNames )

        viewManager = LinearLayoutManager(requireContext())


        ingredientViewModel.listOfIngredientNames.observe(viewLifecycleOwner, {
            ingredientListAdapter.ingredientsUnFiltered = ingredientViewModel.listOfIngredientNames.value!!
            ingredientListAdapter.notifyDataSetChanged()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drink, container, false)
    }

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
            }//TODO: jakaś walidacja, typu selected.count >= 1
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
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddDrinkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddDrinkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}