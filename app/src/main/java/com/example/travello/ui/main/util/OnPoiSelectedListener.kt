package com.example.travello.ui.main.util

import com.example.travello.ui.main.model.PointOfInterest

interface OnPoiSelectedListener {
    fun onPoiSelected(poi: PointOfInterest?)
}