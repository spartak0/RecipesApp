package com.spartak.recipesapp.di

import com.spartak.recipesapp.ui.details_screen.DetailsFragment
import com.spartak.recipesapp.ui.home_screen.HomeFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(detailsFragment: DetailsFragment)
}