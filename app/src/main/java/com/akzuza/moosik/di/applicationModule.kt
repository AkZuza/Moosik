package com.akzuza.moosik.di

import android.content.ContentResolver
import android.content.Context
import com.akzuza.moosik.screens.home.HomeViewModel
import com.akzuza.moosik.screens.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun getContentResolver(context: Context): ContentResolver = context.contentResolver

val applicationModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    factory { getContentResolver(get()) }
}