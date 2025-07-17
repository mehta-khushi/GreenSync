package com.example.greensync.data

data class Appliance(
    val id: Int,
    val name: String,
    val powerRating: Double,
    val usageHours: Double,
    val energyConsumption: Double = powerRating * usageHours
)