package com.akzuza.moosik.di

import com.akzuza.moosik.screens.home.HomeViewModel
import com.akzuza.moosik.screens.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val applicationModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
}