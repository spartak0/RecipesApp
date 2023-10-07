package com.spartak.recipesapp.app

import android.app.Application
import android.content.Context
import com.spartak.recipesapp.di.AppComponent
import com.spartak.recipesapp.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}
val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }