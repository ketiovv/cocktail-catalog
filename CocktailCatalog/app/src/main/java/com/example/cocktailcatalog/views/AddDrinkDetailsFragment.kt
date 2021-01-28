package com.example.cocktailcatalog.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.LocalDrinkNameAndIngredients

private const val ARG_FIRST_PAGE_DATA = "firstPageData"

class AddDrinkDetailsFragment : Fragment() {
    private lateinit var firstPageData :LocalDrinkNameAndIngredients

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstPageData = it.getParcelable(ARG_FIRST_PAGE_DATA)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drink_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("test",firstPageData.name)
        Log.d("test","ingredient list: ")
        for (x in firstPageData.ingredients){
            Log.d("test", x.name)
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