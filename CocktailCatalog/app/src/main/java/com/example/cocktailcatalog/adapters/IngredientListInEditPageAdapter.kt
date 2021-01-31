package com.example.cocktailcatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.models.entities.IngredientNamesList

class IngredientListInEditPageAdapter(
    var ingredients: LiveData<List<Ingredient>>,
    var deleteCallback:((i: Ingredient) -> Unit))
    : RecyclerView.Adapter<IngredientListAdapter.Holder>(){
    class Holder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientListAdapter.Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_ingredient_with_delete, parent, false) as View

        return IngredientListAdapter.Holder(view)
    }

    override fun onBindViewHolder(holder: IngredientListAdapter.Holder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.textViewIDName)
        name.text = ingredients.value?.get(position)?.name ?: "error"

        val deleteBtn = holder.itemView.findViewById<ImageButton>(R.id.buttonDeleteIngredient)
        deleteBtn.setOnClickListener {
            val ingredient = ingredients.value?.get(position)
            if (ingredient != null) deleteCallback(ingredient)
        }
    }

    override fun getItemCount() = ingredients.value?.size?:0

}