package com.example.travello.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travello.R
import com.example.travello.ui.main.model.PointOfInterest
import com.example.travello.ui.main.util.OnPoiSelectedListener

class PoiAdapter(private val context: Context, private val listener: OnPoiSelectedListener?) :
    ListAdapter<PointOfInterest, PoiAdapter.ViewHolder>(PointOfInterestDiffCallback) {

    class ViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
        private val nameTv: TextView = view.findViewById(R.id.poi_name_tv)
        private val cityTv: TextView = view.findViewById(R.id.poi_city_tv)
        private val countryTv: TextView = view.findViewById(R.id.poi_country_tv)
        private val img: ImageView = view.findViewById(R.id.poi_img)

        fun bind(poi: PointOfInterest, listener: OnPoiSelectedListener?) {
            nameTv.text = poi.name
            cityTv.text = poi.city
            countryTv.text = poi.country
            setPoiImage(poi)

            itemView.setOnClickListener {
                listener?.onPoiSelected(poi)
            }
        }

        private fun setPoiImage(poi: PointOfInterest) {
            Glide.with(context)
                .load(poi.photo)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_photo)
                .override(120, 60)
                .centerCrop()
                .into(img)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_poi, viewGroup, false)

        return ViewHolder(context, view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val poi = getItem(position)
        viewHolder.bind(poi, listener)
    }

}