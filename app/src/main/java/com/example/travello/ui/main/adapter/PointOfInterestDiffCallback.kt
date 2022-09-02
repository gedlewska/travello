package com.example.travello.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.travello.ui.main.model.PointOfInterest

object PointOfInterestDiffCallback : DiffUtil.ItemCallback<PointOfInterest>() {
    override fun areItemsTheSame(oldItem: PointOfInterest, newItem: PointOfInterest): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PointOfInterest, newItem: PointOfInterest): Boolean {
        return oldItem.name == newItem.name
                && oldItem.description == newItem.description
                && oldItem.city == newItem.city
                && oldItem.country == newItem.country
                && oldItem.address == newItem.address
                && oldItem.photo == newItem.photo
    }
}