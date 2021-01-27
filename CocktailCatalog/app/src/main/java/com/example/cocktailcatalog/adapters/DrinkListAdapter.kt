package com.example.cocktailcatalog.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.models.entities.DrinkList
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.Drink


class DrinkListAdapter(var drinks: LiveData<DrinkList>, var clickCallback: ((d: Drink) -> Unit)) :RecyclerView.Adapter<DrinkListAdapter.Holder>(){
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

        if(drinks.value?.get(position)?.name != "") {
            textViewTitle.text =  drinks.value?.get(position)?.name
            rowDrink.setOnClickListener {
                clickCallback(drinks.value?.get(position)!!)
            }
        }
        else{
            textViewTitle.text = holder.itemView.resources.getString(R.string.no_drinks_found)
        }

    }

    override fun getItemCount(): Int {
        return drinks.value?.size?:0
    }


}