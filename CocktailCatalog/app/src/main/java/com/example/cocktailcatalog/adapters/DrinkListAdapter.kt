package com.example.cocktailcatalog.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.models.entities.DrinkList
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.viewmodels.DrinkViewModel


class DrinkListAdapter(var drinks: LiveData<DrinkList>) :RecyclerView.Adapter<DrinkListAdapter.Holder>(){
    class Holder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_drinks,parent,false) as View

        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.textViewDrinkName)
        val rowDrink = holder.itemView.findViewById<LinearLayout>(R.id.row_drink)

        textViewTitle.text =  drinks.value?.get(position)?.name

        rowDrink.setOnClickListener {
            DrinkViewModel.selectedDrink =  drinks.value?.get(position)!!
            it.findNavController().navigate(R.id.action_searchFragment_to_drinkDetailsFragment)
        }

    }

    override fun getItemCount(): Int {
        return drinks.value?.size?:0
    }


}