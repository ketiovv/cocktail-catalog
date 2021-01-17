package com.example.cocktailcatalog.Adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.Model.DrinkList
import com.example.cocktailcatalog.Model.IngredientNamesList
import com.example.cocktailcatalog.R


class IngredientListAdapter(var ingredients: LiveData<IngredientNamesList>) :RecyclerView.Adapter<IngredientListAdapter.Holder>(){
    class Holder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_ingredient,parent,false) as View

        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.textViewIName)

        textViewTitle.text =  ingredients.value?.get(position)


    }

    override fun getItemCount(): Int {
        return ingredients.value?.size?:0
    }


}