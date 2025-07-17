package com.example.greensync.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.greensync.R
import com.example.greensync.adapters.TipsAdapter

class TipsFragment : Fragment() {

    private lateinit var tipsList: List<String>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tips, container, false)

        tipsList = listOf(
            "Turn off lights when not in use.",
            "Unplug devices to prevent phantom loads.",
            "Use energy-efficient LED bulbs.",
            "Set your AC at 24°C for optimal savings.",
            "Run washing machines with full loads.",
            "Dry clothes in sunlight instead of using dryers."
        )

        recyclerView = view.findViewById(R.id.recycler_tips)
        recyclerView.adapter = TipsAdapter(tipsList)

        return view
    }
}
