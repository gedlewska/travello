package com.example.travello.ui.main.di.module

import com.example.travello.ui.main.firestore.FirestorePoiDataSource
import com.example.travello.ui.main.repository.Repository
import com.example.travello.ui.main.viewmodel.PoiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single { FirestorePoiDataSource() }

    single { Repository(get()) }

    viewModel { PoiViewModel(get()) }
}