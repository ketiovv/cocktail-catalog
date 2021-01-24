package com.example.cocktailcatalog.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.adapters.IngredientListAdapter
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import kotlinx.android.synthetic.main.fragment_search_by_ingredient.*
import kotlinx.android.synthetic.main.fragment_search_drink.*

class SearchByIngredientFragment : Fragment() {

    private lateinit var viewModel: IngredientViewModel
    private lateinit var ingredientListAdapter: IngredientListAdapter
    private lateinit var ingredientViewModel: IngredientViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewManager = LinearLayoutManager(requireContext())
        ingredientViewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)
        ingredientViewModel.getIngredientNameList()

        ingredientListAdapter = IngredientListAdapter(ingredientViewModel.listOfIngredientNames)

        ingredientViewModel.listOfIngredientNames.observe(viewLifecycleOwner, {
            ingredientListAdapter.notifyDataSetChanged()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_ingredient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewIngredientListSearch.apply{
            adapter = ingredientListAdapter
            layoutManager=viewManager
        }

    }

    companion object {


        @JvmStatic
        fun newInstance() = SearchByIngredientFragment()
    }
}