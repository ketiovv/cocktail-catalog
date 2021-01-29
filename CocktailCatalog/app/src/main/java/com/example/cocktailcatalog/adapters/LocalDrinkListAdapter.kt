package com.example.cocktailcatalog.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.adapters.LocalDrinkListAdapter.DrinkHolder
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.LocalDrink

class LocalDrinkListAdapter(var localDrinks: LiveData<List<LocalDrink>>,
                            var clickCallback: ((d: LocalDrink) -> Unit))
    : RecyclerView.Adapter<DrinkHolder>() {
    inner class DrinkHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_drinks,parent,false) as View

        return DrinkHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkHolder, position: Int) {
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.textViewDrinkName)
        val rowDrink = holder.itemView.findViewById<LinearLayout>(R.id.row_drink)

        if(localDrinks!!.value?.get(position)?.name != "") {
            textViewTitle.text =  localDrinks!!.value?.get(position)?.name
            Log.d("test", localDrinks!!.value?.get(position)?.name?:"xddd")
            rowDrink.setOnClickListener {
                clickCallback(localDrinks!!.value?.get(position)!!)
            }
        }
        else{
            textViewTitle.text = holder.itemView.resources.getString(R.string.no_drinks_found)
        }
    }

    override fun getItemCount() = localDrinks?.value?.size?:0
}