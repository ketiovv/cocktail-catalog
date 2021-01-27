package com.example.cocktailcatalog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.cocktailcatalog.R
import com.example.cocktailcatalog.models.entities.SpinnerItemState

class IngredientSpinnerAdapter(
        context: Context,
        resource: Int,
        var IngredientSpinnerItems: ArrayList<SpinnerItemState>
)
    : ArrayAdapter<SpinnerItemState>(context, resource) {
    class ViewHolder(var textView: TextView, var checkBox: CheckBox)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var myConvertView = convertView
        val holder:ViewHolder

        if (myConvertView == null){
            val layoutInflater = LayoutInflater.from(context)
            myConvertView = layoutInflater.inflate(R.layout.row_ingredient, null)
            val tv = myConvertView.findViewById<TextView>(R.id.textViewIName)
            val cb = myConvertView.findViewById<CheckBox>(R.id.checkBoxSelectIngredient)
            holder = ViewHolder(tv,cb)
            myConvertView.tag = holder
        }
        else{
            holder = myConvertView.tag as ViewHolder
        }

        holder.textView.text = IngredientSpinnerItems.get(position).title

        return myConvertView!!
    }
}