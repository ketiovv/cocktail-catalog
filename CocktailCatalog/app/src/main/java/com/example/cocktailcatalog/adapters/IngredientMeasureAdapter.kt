package com.example.cocktailcatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.Ingredient

class IngredientMeasureAdapter(var ingredients: ArrayList<Ingredient>):RecyclerView.Adapter<IngredientMeasureAdapter.IngredientHolder>() {
    inner class IngredientHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_igredient_measure, parent,false)
        return IngredientHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val ingredientName = holder.itemView.findViewById<TextView>(R.id.textViewIngredientName)
        ingredientName.text = ingredients[position].name

        val spinner = holder.itemView.findViewById<Spinner>(R.id.spinnerUnit)
        val measureValue = holder.itemView.findViewById<TextView>(R.id.editTextNewDrinkIngredientMeasure)

        if (spinner != null) {
            val adapter = ArrayAdapter(
                holder.itemView.context,
                android.R.layout.simple_spinner_item, arrayOf("ml", "xd")
            )
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                positionInSpinner: Int,
                id: Long
            ) {
                ingredients[position].measure = measureValue.text.toString() + " " + spinner.selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                ingredients[position].measure = measureValue.text.toString() + " " + spinner.selectedItem
            }
        }

        measureValue.addTextChangedListener(){
            ingredients[position].measure = measureValue.text.toString() + " " + spinner.selectedItem
        }
    }

    override fun getItemCount() = ingredients.size
}