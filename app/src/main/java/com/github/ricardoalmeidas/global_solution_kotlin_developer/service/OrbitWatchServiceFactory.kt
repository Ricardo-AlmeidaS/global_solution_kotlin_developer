package com.github.ricardoalmeidas.global_solution_kotlin_developer.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OrbitWatchServiceFactory {

    fun create(): OrbitWatchService {
        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.orbitwatch.example.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(OrbitWatchService::class.java)
    }
}
