package com.spartak.recipesapp.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spartak.recipesapp.ui.MultiViewModelFactory
import com.spartak.recipesapp.ui.details_screen.DetailsViewModel
import com.spartak.recipesapp.ui.home_screen.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelBinds {

    @Binds
    fun bindViewModelFactory(factory: MultiViewModelFactory): ViewModelProvider.Factory

    @Binds
    @[IntoMap ViewModelKey(HomeViewModel::class)]
    fun provideMainViewModel(mainViewModel: HomeViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    fun provideDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

}