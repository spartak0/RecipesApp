package com.spartak.recipesapp.di

import com.spartak.recipesapp.data.network.repository.RecipeRepositoryImpl
import com.spartak.recipesapp.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module

@Module
interface BindModule {
    @Binds
    fun bindRecipeRepository(recipeRepositoryImpl: RecipeRepositoryImpl): RecipeRepository
}