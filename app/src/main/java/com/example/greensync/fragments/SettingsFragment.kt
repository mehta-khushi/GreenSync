package com.example.greensync.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.greensync.R
import com.example.greensync.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var notificationCheckerLayout: LinearLayout
    private lateinit var themeSwitch: Switch
    private lateinit var unitsSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val sharedPrefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        // Request notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        // Create notification channel
        createNotificationChannel()

        // Notification checker layout
        notificationCheckerLayout = binding.notificationCheckerLayout
        notificationCheckerLayout.setOnClickListener {
            sendDummyNotification()
        }

        // Theme switch (dark mode)
        themeSwitch = binding.switchTheme

        // Ensure dark mode is OFF by default (only on first app launch)
        val isFirstRun = sharedPrefs.getBoolean("first_run", true)
        if (isFirstRun) {
            sharedPrefs.edit().putBoolean("dark_mode", false).apply()
            sharedPrefs.edit().putBoolean("first_run", false).apply()
        }

        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        themeSwitch.isChecked = isDarkMode

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()
        }

        // Tips notification switch
        val tipsSwitch = binding.switchNotifications
        tipsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                requireContext(),
                if (isChecked) "Tips notifications enabled" else "Tips notifications disabled",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Units spinner setup
        unitsSpinner = binding.spinnerUnits
        val units = listOf("kWh", "Wh")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitsSpinner.adapter = adapter

        val savedUnit = sharedPrefs.getString("selected_unit", "kWh")
        unitsSpinner.setSelection(units.indexOf(savedUnit))

        unitsSpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedUnit = units[position]
                sharedPrefs.edit().putString("selected_unit", selectedUnit).apply()
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        })

        // Clear all saved data
        binding.btnClearData.setOnClickListener {
            sharedPrefs.edit().clear().apply()

            themeSwitch.isChecked = false
            tipsSwitch.isChecked = false
            unitsSpinner.setSelection(0)

            Toast.makeText(requireContext(), "All data cleared.", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Power Consumption Notifications"
            val description = "Notifications to check tips and updates"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("power_consumption_channel", name, importance).apply {
                this.description = description
            }
            val notificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendDummyNotification() {
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(requireContext(), "power_consumption_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Notification Checker")
            .setContentText("This is a dummy notification to check if notifications are working.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
