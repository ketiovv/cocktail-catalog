package com.example.cocktailcatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.Ingredient
import com.google.android.material.transition.Hold

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
    }

    override fun getItemCount() = ingredients.size
}