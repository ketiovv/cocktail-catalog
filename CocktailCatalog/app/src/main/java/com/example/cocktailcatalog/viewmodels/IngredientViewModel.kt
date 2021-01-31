package com.example.cocktailcatalog.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.cocktailcatalog.api.deserializers.IngredientsNamesListDeserializer

import com.example.cocktailcatalog.api.ApiRoutes
import com.example.cocktailcatalog.api.IApiRequest
import com.example.cocktailcatalog.models.AppDatabase
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.models.entities.IngredientNamesList
import com.example.cocktailcatalog.models.entities.LocalDrink
import com.example.cocktailcatalog.models.repositories.IngredientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse

class IngredientViewModel(application: Application) : AndroidViewModel(application) {
    var listOfIngredientNames = MutableLiveData<IngredientNamesList>()

    private val ingredientRepository = IngredientRepository(AppDatabase.getDatabase(application).ingredientDao())

    val drinkId = MutableLiveData<Int>()
    val drinkIngredients: LiveData<List<Ingredient>>

    init {
        drinkId.value = 0

        drinkIngredients = Transformations.switchMap(drinkId){
            if (it == 0){
                return@switchMap ingredientRepository.allIngredients
            }
            else{
                return@switchMap ingredientRepository.getIngredientsInDrink(it)
            }
        }
    }


    // DB METHODS
    suspend fun addIngredientToLocalDatabase(name: String, measure: String, drinkId: Long): Long = withContext(Dispatchers.IO){
        val ingredient = Ingredient(0, name, measure, drinkId)
        return@withContext ingredientRepository.add(ingredient)
    }

    fun deleteIngredientFromLocalDb(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.delete(ingredient)
        }
    }

    // API METHODS
    fun getIngredientNameList(){
        GlobalScope.launch(Dispatchers.IO) {

            val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                    .addConverterFactory(ApiRoutes
                            .bulidGsonConverter(IngredientNamesList::class.java, IngredientsNamesListDeserializer())).build()
                    .create(IApiRequest::class.java)

            val response  = api.getIngredientNameList().awaitResponse()

            if (response.isSuccessful){
                var data = response.body()

               // Log.d("myTagData", data.toString())
                unFilterIngredients.postValue(data)

                listOfIngredientNames.postValue(data)



                //Log.d("myTag", listOfIngredientNames.value.toString())

            }
            else{
                Log.d("api-connection","response failed")
            }
        }
    }
    companion object{
            //TODO: Use viewmodel factory?
        lateinit var selectedIngredients : IngredientNamesList
        var unFilterIngredients = MutableLiveData<IngredientNamesList>()
    }
}