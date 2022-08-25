package com.example.travello.ui.main.firestore

import android.util.Log
import com.example.travello.ui.main.model.PointOfInterest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestorePoiDataSource {

    companion object {
        private const val TAG = "FirestorePoiDataSource"
    }

    fun getPois(city: String): Flow<List<PointOfInterest>> = callbackFlow {

        try {
            val poisCollection = FirebaseFirestore.getInstance()
                .collection("points_of_interest")

            val query = poisCollection.whereEqualTo(PointOfInterest.FIELD_CITY, city)

            val subscription = query.addSnapshotListener { snapshot, exception ->
                if (exception != null || snapshot == null) {
                    return@addSnapshotListener
                }
                try {
                    trySend(snapshot.toObjects(PointOfInterest::class.java)).isSuccess
                } catch (e: Throwable) {
                    Log.e(TAG, "Points of interest couldn't be sent to the flow")
                }
            }

            awaitClose { subscription.remove() }

        } catch (e: Throwable) {
            Log.e(TAG, "Firebase cannot be initialized. The stream of data flow will be stoped")
            close(e)
        }

    }

    fun getPois(): Flow<List<PointOfInterest>> = callbackFlow {

        var poisCollection: CollectionReference? = null
        try {
            poisCollection = FirebaseFirestore.getInstance().collection("points_of_interest")
        } catch (e: Throwable) {
            Log.e(TAG, "Firebase cannot be initialized. The stream of data flow will be stoped")
            close(e)
        }

        val subscription = poisCollection?.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                return@addSnapshotListener
            }
            try {
                trySend(snapshot.toObjects(PointOfInterest::class.java)).isSuccess
            } catch (e: Throwable) {
                Log.e(TAG, "Points of interest couldn't be sent to the flow")
            }
        }

        awaitClose { subscription?.remove() }
    }

}