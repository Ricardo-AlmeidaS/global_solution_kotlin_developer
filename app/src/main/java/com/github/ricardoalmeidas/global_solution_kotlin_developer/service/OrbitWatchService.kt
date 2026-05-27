package com.github.ricardoalmeidas.global_solution_kotlin_developer.service

import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.SatelliteResponse
import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.SpaceAlert
import retrofit2.Response
import retrofit2.http.GET

interface OrbitWatchService {

    @GET("satellites")
    suspend fun getSatellites(): Response<SatelliteResponse>

    @GET("alerts")
    suspend fun getAlerts(): Response<List<SpaceAlert>>
}
