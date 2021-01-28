package com.example.cocktailcatalog.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cocktailcatalog.api.ApiRoutes
import com.example.cocktailcatalog.api.Deserializers.DrinkByIdDeserializer
import com.example.cocktailcatalog.api.Deserializers.DrinkListByIngrediendDeserializer
import com.example.cocktailcatalog.api.Deserializers.DrinkListDeserializer
import com.example.cocktailcatalog.api.IApiRequest
import com.example.cocktailcatalog.models.AppDatabase
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.DrinkList
import com.example.cocktailcatalog.models.entities.IngredientNamesList
import com.example.cocktailcatalog.models.entities.LocalDrink
import com.example.cocktailcatalog.models.repositories.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse

class DrinkViewModel(application: Application) : AndroidViewModel(application) {
    var listOfDrinks = MutableLiveData<DrinkList>()

    private val drinkRepository = DrinkRepository(AppDatabase.getDatabase(application).drinkDao())
    private val allLocalDrinks = drinkRepository.allDrinks

    // DB METHODS
    suspend fun addDrinkToLocalDatabase(
            name: String,
            category: String,
            instructions: String,
            imgUrl: String,
            glassType: String,
            alcoholic: String,
        ): Long = withContext(Dispatchers.IO){

        val drink = LocalDrink(0, name, category, instructions, imgUrl, glassType, alcoholic)
        return@withContext drinkRepository.add(drink)
    }

    // API METHODS
    fun getDrinksByName(name: String){

        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                .addConverterFactory(ApiRoutes.bulidGsonConverter(DrinkList::class.java, DrinkListDeserializer())).build()
                .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {

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

    fun getDrinksByIngrediends(ingredientList : IngredientNamesList)
    {
        //   val request = ingredientList.toString().trimStart('[').trimEnd(']').replace(" ","")
        var request:String = ""
        for(i in ingredientList)
        {
            request += "${i},"

        }
        request = request.trimEnd(',')
        // Log.d("myTag", request)
        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                .addConverterFactory(ApiRoutes.bulidGsonConverter(DrinkList::class.java, DrinkListByIngrediendDeserializer())).build()
                .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            val response  = api.getDrinksByIngredients(request).awaitResponse()

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

    fun getDrinksById(id: String, doneCallback: ((d: Drink) -> Unit)){

        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                .addConverterFactory(ApiRoutes.bulidGsonConverter(Drink::class.java, DrinkByIdDeserializer())).build()
                .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            val response  = api.getDrinkById(id).awaitResponse()

            if (response.isSuccessful){

                val data = response.body()
                if (data != null) {
                    doneCallback(data)
                }
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
