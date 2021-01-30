package com.example.cocktailcatalog.Adapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.os.Bundle
import android.widget.SectionIndexer
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailcatalog.Model.Drink
import com.example.cocktailcatalog.Model.DrinkList
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.ViewModel.DrinkViewModel
import javax.security.auth.callback.Callback


class  DrinkListAdapter(var drinks: LiveData<DrinkList>, var clickCallback: ((d: Drink) -> Unit)) :RecyclerView.Adapter<DrinkListAdapter.Holder>(),
    SectionIndexer {
    class Holder(view:View):RecyclerView.ViewHolder(view)

    private lateinit var mSectionPositions: ArrayList<Int>

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

    override fun getSections(): Array<String> {
        var sections: MutableList<String> = ArrayList()
        mSectionPositions = ArrayList()
//        if(itemCount != 0){
//            var i = 0
//            val size = itemCount
//            while (i < size) {
//                val section = drinks.value?.get(i)?.name?.get(0).toString().toUpperCase()
//                if (!sections.contains(section)) {
//                    sections.add(section)
//                    mSectionPositions.add(i)
//                }
//                i++
//            }
//        }
//        else {
//            "chuj"
//            sections.add("A")
//            mSectionPositions.add(0)
//        }
            return sections.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions[sectionIndex];
    }

    override fun getSectionForPosition(position: Int): Int {
        return 0;
    }

}