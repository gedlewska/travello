package com.example.travello.ui.main.repository

import android.util.Log
import com.example.travello.ui.main.firestore.FirestorePoiDataSource
import com.example.travello.ui.main.model.PointOfInterest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class Repository(private val firestorePoiDataSource: FirestorePoiDataSource) {

    companion object {
        private const val TAG = "Repository"
    }

    fun pois(): Flow<List<PointOfInterest>> =
        firestorePoiDataSource.getPois().catch { exception -> notifyError(exception) }

    fun pois(city: String?): Flow<List<PointOfInterest>> {
        if (city == null || city.isEmpty())
            return firestorePoiDataSource.getPois().catch { exception -> notifyError(exception) }
        return firestorePoiDataSource.getPois(city).catch { exception -> notifyError(exception) }
    }

    private fun notifyError(exception: Throwable) {
        Log.e(TAG, "Couldn't get points of interest")
    }

}