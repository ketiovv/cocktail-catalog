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
import com.example.cocktailcatalog.R


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

        textViewTitle.text =  drinks.value?.get(position)?.name


    }

    override fun getItemCount(): Int {
        return drinks.value?.size?:0
    }


}