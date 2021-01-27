package com.example.cocktailcatalog.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktailcatalog.api.ApiRoutes
import com.example.cocktailcatalog.api.DrinkListDeserializer
import com.example.cocktailcatalog.api.IApiRequest
import com.example.cocktailcatalog.models.AppDatabase
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.DrinkList
import com.example.cocktailcatalog.models.entities.LocalDrink
import com.example.cocktailcatalog.models.repositories.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse

class DrinkViewModel(application: Application) : AndroidViewModel(application) {
    var listOfDrinks = MutableLiveData<DrinkList>()

    private val drinkRepository = DrinkRepository(AppDatabase.getDatabase(application).drinkDao())

    fun addDrinkToLocalDatabase(
            name: String,
            category: String,
            instructions: String,
            imgUrl: String,
            glassType: String,
            alcoholic: String, ){
        val drink = LocalDrink(
                0,
                name,
                category,
                instructions,
                imgUrl,
                glassType,
                alcoholic
        )

        viewModelScope.launch {
            drinkRepository.add(drink)
        }
    }

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