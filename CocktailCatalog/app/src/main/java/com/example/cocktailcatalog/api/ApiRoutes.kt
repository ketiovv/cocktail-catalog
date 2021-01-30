package com.example.cocktailcatalog.api

import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class ApiRoutes {
    companion object{
        const val BASE_URL = "https://www.thecocktaildb.com/api/json/v2/9973533/"

        fun bulidGsonConverter(type: Type, typeAdapter: Any): GsonConverterFactory {


            val gsonBuilder: GsonBuilder = GsonBuilder().registerTypeAdapter(type, typeAdapter)


            val myGson = gsonBuilder.create()

            return GsonConverterFactory.create(myGson)
        }
    }
}