package com.example.cocktailcatalog.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailcatalog.api.ApiRoutes
import com.example.cocktailcatalog.api.DrinkListDeserializer
import com.example.cocktailcatalog.api.IApiRequest
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.DrinkList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse

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

    companion object{
        lateinit var selectedDrink: Drink
    }
}