package com.spartak.recipesapp.di

import android.content.Context
import com.spartak.recipesapp.ui.details_screen.DetailsFragment
import com.spartak.recipesapp.ui.favorite_screen.FavoriteFragment
import com.spartak.recipesapp.ui.home_screen.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}