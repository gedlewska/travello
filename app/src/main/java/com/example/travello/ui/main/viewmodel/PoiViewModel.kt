package com.example.travello.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travello.ui.main.model.PointOfInterest
import com.example.travello.ui.main.repository.Repository
import kotlinx.coroutines.launch

class PoiViewModel(private val repository: Repository) : ViewModel() {

    private val mutablePois = MutableLiveData<List<PointOfInterest>>()
    val pois: LiveData<List<PointOfInterest>> get() = mutablePois

    private val mutableCity = MutableLiveData<String?>()
    private val selectedCity: LiveData<String?> get() = mutableCity

    private val mutableSelectedPoi = MutableLiveData<PointOfInterest>()
    val selectedPoi: LiveData<PointOfInterest> get() = mutableSelectedPoi

    companion object {
        private const val TAG = "PoiViewModel"
    }

    private fun getPois() {
        viewModelScope.launch {
            repository.pois(selectedCity.value).collect { pois ->
                mutablePois.value = pois
            }
        }
    }

    fun city(city: String?) {
        mutableCity.value = city
        getPois()
    }

    fun selectPoi(poi: PointOfInterest) {
        mutableSelectedPoi.value = poi
    }
}