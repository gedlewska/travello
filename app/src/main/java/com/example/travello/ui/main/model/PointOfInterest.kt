package com.example.travello.ui.main.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
public class PointOfInterest {

    companion object {
        const val FIELD_CITY = "city"
        const val FIELD_COUNTRY = "country"
        const val FIELD_DESCRIPTION = "description"
        const val FIELD_NAME = "name"
        const val FIELD_ADDRESS = "address"
        const val FIELD_PHOTO = "photo"
    }

    val name: String? = null
    val city: String? = null
    val country: String? = null
    val description: String? = null
    val address: String? = null
    val photo: String? = null

}