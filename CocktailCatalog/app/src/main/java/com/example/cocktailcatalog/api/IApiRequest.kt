package com.example.cocktailcatalog.api

import com.example.cocktailcatalog.models.DrinkList
import com.example.cocktailcatalog.models.IngredientNamesList
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface IApiRequest {
    @GET("search.php?")
    fun getDrinksByName(@Query("s") name: String): Call<DrinkList>

    @GET("list.php?i=list")
    fun getIngredientNameList(): Call<IngredientNamesList>
}