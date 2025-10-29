package com.example.movies00.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.java


interface MovieService {

    @GET("/")
    suspend fun searchMovies(@Query("apikey") apiKey: String = "326b2db8", @Query("s") searchTerm: String): MovieResponse


    @GET("/")
    suspend fun getMovieById(@Query("apikey") apiKey: String = "326b2db8", @Query("i") id: String): Movie

    companion object {
        fun getInstance(): MovieService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MovieService::class.java)
        }
    }
}





