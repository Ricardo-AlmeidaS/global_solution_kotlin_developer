package com.github.ricardoalmeidas.global_solution_kotlin_developer.model

data class SatelliteResponse(
    val satellites: List<Satellite>
)

data class Satellite(
    val id: String,
    val name: String,
    val type: String,
    val country: String,
    val altitudeKm: Int,
    val inclination: Double,
    val launchYear: Int,
    val status: String,
    val description: String
)
