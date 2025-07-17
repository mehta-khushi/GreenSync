package com.example.greensync.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greensync.R

class TipsAdapter(private val tips: List<String>) : RecyclerView.Adapter<TipsAdapter.TipViewHolder>() {

    inner class TipViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tipText: TextView = view.findViewById(R.id.text_tip)
        val icon: ImageView = view.findViewById(R.id.icon_tip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tip, parent, false)
        return TipViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        holder.tipText.text = tips[position]
        // Optionally change icon based on position or type
    }

    override fun getItemCount(): Int = tips.size
}
