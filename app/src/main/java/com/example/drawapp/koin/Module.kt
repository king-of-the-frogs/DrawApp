package com.example.drawapp.koin

import com.example.drawapp.CanvasViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        CanvasViewModel()
    }
}