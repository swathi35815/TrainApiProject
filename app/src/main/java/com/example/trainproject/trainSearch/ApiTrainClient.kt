package com.example.trainproject.trainSearch

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiTrainInterface{
    @Headers(
        "content-type: application/json",
        "X-RapidAPI-Key: a8154f7768msh18428a70f83046cp1e5b8ajsna9e7104d2260",
        "X-RapidAPI-Host: trains.p.rapidapi.com"
    )
    /*@POST("/")
    fun searchTrain(@Body request: String): Call<List<TrainData>>*/

    @POST("/")
    fun searchTrain(@Body body : RequestBody): Call<List<TrainData>>
}

class ApiTrainClient {
    companion object {

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://trains.p.rapidapi.com/") // Base URL for the API
            .addConverterFactory(GsonConverterFactory.create()) // Converter factory for Gson
            .build()
            .create(ApiTrainInterface::class.java) // Create an implementation of the API endpoints
    }
}