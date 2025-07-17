package com.example.greensync.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.greensync.data.Appliance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("GreenSyncPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveAppliances(appliances: List<Appliance>) {
        val json = gson.toJson(appliances)
        prefs.edit().putString("appliance_list", json).apply()
    }

    fun getAppliances(): List<Appliance> {
        val json = prefs.getString("appliance_list", null) ?: return emptyList()
        val type = object : TypeToken<List<Appliance>>() {}.type
        return gson.fromJson(json, type)
    }
}
