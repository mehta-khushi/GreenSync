package com.example.greensync.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greensync.data.Appliance
import com.example.greensync.data.ApplianceRepository

class ApplianceViewModel : ViewModel() {
    private val repository = ApplianceRepository()
    private val _appliances = MutableLiveData<List<Appliance>>()
    val appliances: LiveData<List<Appliance>> = _appliances

    init {
        loadAppliances()
    }

    private fun loadAppliances() {
        _appliances.value = repository.getAppliances()
    }

    fun addAppliance(appliance: Appliance) {
        repository.addAppliance(appliance)
        loadAppliances()
    }

    fun deleteAppliance(applianceId: Int) {
        repository.deleteAppliance(applianceId)
        loadAppliances()
    }
}
