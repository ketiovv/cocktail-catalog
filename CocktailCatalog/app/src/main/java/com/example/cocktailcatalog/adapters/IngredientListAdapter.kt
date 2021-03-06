package com.example.cocktailcatalog.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SectionIndexer
import android.widget.TextView
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.models.entities.IngredientNamesList
import com.example.cocktailcatalog.viewmodels.IngredientViewModel
import java.util.*
import kotlin.collections.ArrayList


class IngredientListAdapter(var ingredients: MutableLiveData<IngredientNamesList>) :RecyclerView.Adapter<IngredientListAdapter.Holder>(), Filterable{
    class Holder(view: View):RecyclerView.ViewHolder(view)

    val selectedIngredient: IngredientNamesList = IngredientNamesList()
    var ingredientsUnFiltered = IngredientViewModel.unFilterIngredients.value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_ingredient, parent, false) as View

        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.textViewIName)
        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.checkBoxSelectIngredient)

        textViewTitle.text =  ingredients.value?.get(position)

        checkBox.setOnCheckedChangeListener(null)
        checkBox.isChecked = selectedIngredient.contains(ingredients.value?.get(position)!!)


        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                selectedIngredient.add(ingredients.value?.get(position)!!)
            else if((!isChecked) && selectedIngredient.contains(ingredients.value?.get(position)!!))
                selectedIngredient.remove(ingredients.value?.get(position)!!)
        }

    }

    override fun getItemCount(): Int {
        return ingredients.value?.size?:0
    }
    fun getSelectedItemsCount(): Int {
        return selectedIngredient.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }
    
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val query = charSequence.toString()
                var filtered = IngredientNamesList()
                if (query.isEmpty()) {
                    filtered = ingredientsUnFiltered!!
                } else {
                    for (i in ingredientsUnFiltered!!) {
                        if (i.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                            filtered.add(i)
                        }
                    }
                }
                val results = FilterResults()
                results.count = filtered.size
                results.values = filtered

                return results
            }

            public override fun publishResults(charSequence: CharSequence?, results: FilterResults) {
               ingredients.postValue(results.values as IngredientNamesList)
                this@IngredientListAdapter.notifyDataSetChanged()
            }
        }
    }

}