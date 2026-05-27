package com.github.ricardoalmeidas.global_solution_kotlin_developer.model

data class SpaceAlert(
    val id: String,
    val title: String,
    val description: String,
    val severity: String,
    val region: String,
    val timestamp: String,
    val satelliteName: String
)
