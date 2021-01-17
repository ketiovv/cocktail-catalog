package com.example.cocktailcatalog.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cocktailcatalog.R

class SearchByIngredientFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_ingredient, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchByIngredientFragment()
    }
}