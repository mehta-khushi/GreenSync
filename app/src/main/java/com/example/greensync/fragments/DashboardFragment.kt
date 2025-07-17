package com.example.greensync.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greensync.R
import com.example.greensync.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var lineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root = binding.root

        // Set up the LineChart
        lineChart = binding.lineChart
        setupLineChart()

        // Set current energy usage value
        binding.txvUsageValue.text = "45.2 kWh"

        return root
    }

    private fun setupLineChart() {
        // Sample data for the chart
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 50f))
        entries.add(Entry(1f, 55f))
        entries.add(Entry(2f, 40f))
        entries.add(Entry(3f, 60f))
        entries.add(Entry(4f, 45f))

        val dataSet = LineDataSet(entries, "Energy Consumption")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate() // Refresh the chart
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
