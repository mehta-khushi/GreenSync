package com.example.greensync.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greensync.R
import com.example.greensync.data.Appliance

class ApplianceAdapter(
    private val onDeleteClick: (Int) -> Unit
) : ListAdapter<Appliance, ApplianceAdapter.ViewHolder>(ApplianceDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvApplianceName)
        val tvPower: TextView = itemView.findViewById(R.id.tvPowerRating)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appliance_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appliance = getItem(position)
        holder.tvName.text = appliance.name
        holder.tvPower.text = "Power: ${appliance.powerRating}W | Usage: ${appliance.usageHours}h"
        holder.btnDelete.setOnClickListener {
            onDeleteClick(appliance.id)
        }
    }

    class ApplianceDiffCallback : DiffUtil.ItemCallback<Appliance>() {
        override fun areItemsTheSame(oldItem: Appliance, newItem: Appliance): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Appliance, newItem: Appliance): Boolean = oldItem == newItem
    }
}