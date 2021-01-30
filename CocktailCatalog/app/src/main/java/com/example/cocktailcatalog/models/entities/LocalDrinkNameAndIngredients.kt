package com.example.cocktailcatalog.models.entities

import android.os.Parcel
import android.os.Parcelable

data class LocalDrinkNameAndIngredients(var name: String, var ingredients: ArrayList<Ingredient>)
    :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        arrayListOf<Ingredient>().apply {
            parcel.readList(this, Ingredient::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeList(ingredients)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocalDrinkNameAndIngredients> {
        override fun createFromParcel(parcel: Parcel): LocalDrinkNameAndIngredients {
            return LocalDrinkNameAndIngredients(parcel)
        }

        override fun newArray(size: Int): Array<LocalDrinkNameAndIngredients?> {
            return arrayOfNulls(size)
        }
    }
}