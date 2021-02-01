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
import com.example.cocktailcatalog.models.entities.*
import com.example.cocktailcatalog.models.repositories.DrinkRepository
import com.example.cocktailcatalog.models.repositories.FavouriteDrinkRepository
import com.example.cocktailcatalog.models.repositories.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.util.*

class DrinkViewModel(application: Application) : AndroidViewModel(application) {
    var listOfDrinks = MutableLiveData<DrinkList>()

    var categories = arrayListOf<String>(
        "Ordinary Drink",
        "Cocktail",
        "Milk/Float/Shake",
        "Cocoa",
        "Shot",
        "Coffee / Tea",
        "Homemade Liqueur",
        "Punch / Party Drink",
        "Beer",
        "Soft Drink",
        "Other/Unknown"
    )

    var alphabet = arrayListOf<String>(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "9",
        "'"
    )
    private val historyRepository =
        HistoryRepository(AppDatabase.getDatabase(application).historyDao())
    private val favouriteDrinkRepository = FavouriteDrinkRepository(AppDatabase.getDatabase(application).favouriteDrinkDao())
    private val drinkRepository = DrinkRepository(AppDatabase.getDatabase(application).drinkDao())
    val allLocalDrinks = drinkRepository.allDrinks

    // DB METHODS
    suspend fun addDrinkToLocalDatabase(
        name: String,
        instructions: String,
        imgUrl: String,
        alcoholic: Boolean,
        category: String
    ): Long = withContext(Dispatchers.IO) {

        val drink = LocalDrink(0, name, instructions, imgUrl, alcoholic, category)
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
    ) {
        viewModelScope.launch {
            drinkRepository.update(
                LocalDrink(
                    drink.id,
                    newName,
                    newInstruction,
                    newImageUrl,
                    newAlcoholic, "test")
            )
        }
    }

    fun getRecentDrinks() {

        GlobalScope.launch(Dispatchers.IO) {
            var recentDrinks = DrinkList()
            var history = historyRepository.getAllHistory()

            history.forEach { history ->
                run {
                    var drink =
                        allDrinks.value?.find { drink -> drink.id == history.drinkId.toString() }
                    drink?.let { recentDrinks.add(it) }
                }
            }

            listOfDrinks.postValue(recentDrinks)
        }

    }

    fun getFavouriteDrinks(){
        GlobalScope.launch(Dispatchers.IO) {
            var favouriteDrinks = DrinkList()
            var fav = favouriteDrinkRepository.getAllFavourites()

            fav.forEach { fav ->
                kotlin.run {
                    var drink = allDrinks.value?.find { drink -> drink.id == fav.drinkId.toString() }
                    drink?.let { favouriteDrinks.add(it) }
                }
            }
            listOfDrinks.postValue(favouriteDrinks)
        }
    }

    suspend fun checkIfDrinkIsFavourite(drink: Drink): Boolean = withContext(Dispatchers.IO) {
        var listOfFavourite = favouriteDrinkRepository.getAllFavourites()
        return@withContext listOfFavourite.contains(FavouriteDrink(drink.id.toInt()))
    }


    fun addDrinkToFavourite(drink: Drink){
        viewModelScope.launch {
            val favourite = FavouriteDrink(drink.id.toInt())
            favouriteDrinkRepository.add(favourite)
        }
    }

    fun deleteDrinkFromFavourites(drink: Drink) {
        viewModelScope.launch {
            var fav = FavouriteDrink(drink.id.toInt())
            favouriteDrinkRepository.delete(fav)
        }
    }


    suspend fun addDrinkToHistory(
        drink: Drink
    ) {

        val history = History(drink.id.toInt(), Date())
        historyRepository.add(history)

    }


    // API METHODS
    fun getDrinksByName(name: String) {
        var tmpList = DrinkList()
        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
            .addConverterFactory(
                ApiRoutes.bulidGsonConverter(
                    DrinkList::class.java,
                    DrinkListDeserializer()
                )
            ).build()
            .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getDrinksByName(name).awaitResponse()

            if (response.isSuccessful) {
                var data = response.body()
                if (data != null) {
                    //Log.d("Size", data.size.toString())
                    listOfDrinks.postValue(data)
                }
            } else {
                Log.d("api-connection", "response failed")
            }
        }
    }

    fun getAllDrinks() {
        var tmpList = DrinkList()
        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
            .addConverterFactory(
                ApiRoutes.bulidGsonConverter(
                    DrinkList::class.java,
                    DrinkListDeserializer()
                )
            ).build()
            .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            for (letter in alphabet) {
                val response = api.getDrinksByFirstLetter(letter).awaitResponse()

                if (response.isSuccessful) {
                    var data = response.body()
                    if (data != null) {
//                        if(letter == "A")
//                            listOfDrinks.postValue(data)
//                        else
//                            listOfDrinks.postValue(data)
                        tmpList.addAll(data)
                    }

                } else {
                    Log.d("api-connection", "response failed")
                }
            }
            //Log.d("Size", tmpList.size.toString())
            allDrinks.postValue(tmpList)
            //Log.d("myTag",listOfDrinks.value.toString())
        }
    }

    fun getDrinksByIngrediends(ingredientList: IngredientNamesList) {
        //   val request = ingredientList.toString().trimStart('[').trimEnd(']').replace(" ","")
        var request: String = ""
        for (i in ingredientList) {
            request += "${i},"

        }
        request = request.trimEnd(',')
        // Log.d("myTag", request)
        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
            .addConverterFactory(
                ApiRoutes.bulidGsonConverter(
                    DrinkList::class.java,
                    DrinkListByIngrediendDeserializer()
                )
            ).build()
            .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            val response = api.getDrinksByIngredients(request).awaitResponse()

            if (response.isSuccessful) {
                var data = response.body()
                listOfDrinks.postValue(data)
            } else {
                Log.d("api-connection", "response failed")
            }
        }
    }

    fun getDrinksById(id: String, doneCallback: ((d: Drink) -> Unit)) {

        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
            .addConverterFactory(
                ApiRoutes.bulidGsonConverter(
                    Drink::class.java,
                    DrinkByIdDeserializer()
                )
            ).build()
            .create(IApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            val response = api.getDrinkById(id).awaitResponse()

            if (response.isSuccessful) {

                val data = response.body()
                if (data != null) {
                    doneCallback(data)
                }
                ///Log.d("myTag", listOfDrinks.value.toString())
            } else {
                Log.d("api-connection", "response failed")
            }
        }
    }

    companion object {
        lateinit var selectedDrink: Drink
        var allDrinks = MutableLiveData<DrinkList>()
        lateinit var selectedLocalDrink: LocalDrink
    }
}
