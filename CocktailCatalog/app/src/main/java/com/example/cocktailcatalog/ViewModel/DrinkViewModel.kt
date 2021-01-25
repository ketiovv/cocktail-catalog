package com.example.cocktailcatalog.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailcatalog.Api.ApiRoutes
import com.example.cocktailcatalog.Api.Deserializers.DrinkByIdDeserializer
import com.example.cocktailcatalog.Api.Deserializers.DrinkListByIngrediendDeserializer
import com.example.cocktailcatalog.Api.Deserializers.DrinkListDeserializer
import com.example.cocktailcatalog.Api.IApiRequest
import com.example.cocktailcatalog.Model.Drink
import com.example.cocktailcatalog.Model.DrinkList
import com.example.cocktailcatalog.Model.IngredientNamesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse

class DrinkViewModel : ViewModel() {
    var listOfDrinks = MutableLiveData<DrinkList>()

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
