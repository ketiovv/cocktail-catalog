package com.example.cocktailcatalog.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktailcatalog.api.ApiRoutes
import com.example.cocktailcatalog.api.deserializers.DrinkByIdDeserializer
import com.example.cocktailcatalog.api.deserializers.DrinkListByIngrediendDeserializer
import com.example.cocktailcatalog.api.deserializers.DrinkListDeserializer
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


    var alphabet = arrayListOf<String>("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O",
            "P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","9","'")

    private val drinkRepository = DrinkRepository(AppDatabase.getDatabase(application).drinkDao())
    val allLocalDrinks = drinkRepository.allDrinks

    // DB METHODS
    suspend fun addDrinkToLocalDatabase(
            name: String,
            instructions: String,
            imgUrl: String,
            alcoholic: Boolean,
        ): Long = withContext(Dispatchers.IO){

        val drink = LocalDrink(0, name, instructions, imgUrl, alcoholic)
        return@withContext drinkRepository.add(drink)
    }

    fun deleteDrinkFromLocalDb(drink: LocalDrink) {
        viewModelScope.launch {
            drinkRepository.delete(drink)
        }
    }

    fun updateDrinkInLocalDb(
        drink: LocalDrink,
        newName: String,
        newInstruction: String,
        newImageUrl: String,
        newAlcoholic: Boolean
    ){
        viewModelScope.launch {
            drinkRepository.update(LocalDrink(drink.id, newName,newInstruction, newImageUrl, newAlcoholic))
        }
    }

    // API METHODS
    fun getDrinksByName(name: String){
        var tmpList = DrinkList()
        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                .addConverterFactory(ApiRoutes.bulidGsonConverter(DrinkList::class.java, DrinkListDeserializer())).build()
                .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
                val response  = api.getDrinksByName(name).awaitResponse()

                if (response.isSuccessful){
                    var data = response.body()
                    if (data != null) {
                          listOfDrinks.postValue(data)
                    }
                }
                else{
                    Log.d("api-connection","response failed")
                }
        }
    }

    fun getAllDrinks(){
        var tmpList = DrinkList()
        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                .addConverterFactory(ApiRoutes.bulidGsonConverter(DrinkList::class.java, DrinkListDeserializer())).build()
                .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            for (letter in alphabet){
                val response  = api.getDrinksByFirstLetter(letter).awaitResponse()

                if (response.isSuccessful){
                    var data = response.body()
                    if (data != null) {
//                        if(letter == "A")
//                            listOfDrinks.postValue(data)
//                        else
//                            listOfDrinks.postValue(data)
                        tmpList.addAll(data)
                    }

                }
                else{
                    Log.d("api-connection","response failed")
                }
            }
            allDrinks.postValue(tmpList)
            //Log.d("myTag",listOfDrinks.value.toString())
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
        var allDrinks = MutableLiveData<DrinkList>()
        lateinit var selectedLocalDrink: LocalDrink
    }
}
