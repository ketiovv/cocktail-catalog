package com.example.cocktailcatalog.api

import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.DrinkList
import com.example.cocktailcatalog.models.entities.IngredientNamesList
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface IApiRequest {
    @GET("search.php?")
    fun getDrinksByName(@Query("s") name: String): Call<DrinkList>

    @GET("list.php?i=list")
    fun getIngredientNameList(): Call<IngredientNamesList>

    @GET("filter.php?")
    fun getDrinksByIngredients(@Query("i") ingredients: String): Call<DrinkList>

    @GET("lookup.php?")
    fun getDrinkById(@Query("i") id: String): Call<Drink>
}