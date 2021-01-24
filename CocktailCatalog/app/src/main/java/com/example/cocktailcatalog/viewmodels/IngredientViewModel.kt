package com.example.cocktailcatalog.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailcatalog.api.ApiRoutes
import com.example.cocktailcatalog.api.IApiRequest
import com.example.cocktailcatalog.api.IngredientsNamesListDeserializer
import com.example.cocktailcatalog.models.entities.IngredientNamesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse

class IngredientViewModel : ViewModel() {
    var listOfIngredientNames = MutableLiveData<IngredientNamesList>()

    fun getIngredientNameList(){
        GlobalScope.launch(Dispatchers.IO) {

            val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
                    .addConverterFactory(ApiRoutes
                            .bulidGsonConverter(IngredientNamesList::class.java, IngredientsNamesListDeserializer())).build()
                    .create(IApiRequest::class.java)

            val response  = api.getIngredientNameList().awaitResponse()

            if (response.isSuccessful){
                var data = response.body()

                Log.d("myTagData", data.toString())

                listOfIngredientNames.postValue(data)

                Log.d("myTag", listOfIngredientNames.value.toString())

            }
            else{
                Log.d("api-connection","response failed")
            }
        }
    }
    companion object{

    }
}