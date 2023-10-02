package com.spartak.recipesapp.di

import com.spartak.recipesapp.di.vm.ViewModelBinds
import dagger.Module

@Module(includes = [NetworkModule::class, BindModule::class, ViewModelBinds::class])
class AppModule
