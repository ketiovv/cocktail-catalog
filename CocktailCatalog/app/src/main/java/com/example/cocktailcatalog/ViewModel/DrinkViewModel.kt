package com.example.cocktailcatalog.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailcatalog.Api.ApiRoutes
import com.example.cocktailcatalog.Api.DrinkListDeserializer
import com.example.cocktailcatalog.Api.IApiRequest
import com.example.cocktailcatalog.Model.Drink
import com.example.cocktailcatalog.Model.DrinkList
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class DrinkViewModel : ViewModel() {
    var listOfDrinks = MutableLiveData<DrinkList>()


    fun getDrinksByName(name: String){

        GlobalScope.launch(Dispatchers.IO) {

            val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                .addConverterFactory(ApiRoutes.bulidGsonConverter(DrinkList::class.java, DrinkListDeserializer())).build()
                .create(IApiRequest::class.java)

            val response  = api.getDrinksByName(name).awaitResponse()

            if (response.isSuccessful){
                var data = response.body()


                listOfDrinks.postValue(data)

                ///Log.d("myTag", listOfDrinks.value.toString())

            }
            else{
                Log.d("api-connection","response failed")
            }
        }

    }

}