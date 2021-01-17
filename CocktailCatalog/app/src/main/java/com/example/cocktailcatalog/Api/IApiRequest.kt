package com.example.cocktailcatalog.Api

import com.example.cocktailcatalog.Model.Drink
import com.example.cocktailcatalog.Model.DrinkList
import okhttp3.ResponseBody
import retrofit2.http.GET
import java.util.ArrayList
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiRequest {
    @GET("search.php?")
    fun getDrinksByName(@Query("s") name: String): Call<DrinkList>
}