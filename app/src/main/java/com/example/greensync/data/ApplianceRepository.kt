package com.example.greensync.data

class ApplianceRepository {
    private val appliances = mutableListOf(
        Appliance(1, "Refrigerator", 150.0, 24.0),
        Appliance(2, "LED Lights", 20.0, 10.0)
    )
    private var nextId = 3

    fun getAppliances(): List<Appliance> = appliances.toList()

    fun addAppliance(appliance: Appliance) {
        appliances.add(appliance.copy(id = nextId++))
    }

    fun deleteAppliance(applianceId: Int) {
        appliances.removeIf { it.id == applianceId }
    }

    fun clearAllAppliances() {
        appliances.clear()
        nextId = 1
    }
}
