package com.spartak.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.spartak.recipesapp.data.database.converter.IngredientsConverter
import com.spartak.recipesapp.data.database.dao.RecipeDao
import com.spartak.recipesapp.data.database.db.RecipeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    fun provideIngredientsConverter(): IngredientsConverter = IngredientsConverter()

    @Provides
    @Singleton
    fun provideRecipeDatabase(
        context: Context,
        ingredientsConverter: IngredientsConverter
    ): RecipeDatabase =
        Room.databaseBuilder(context, RecipeDatabase::class.java, RecipeDatabase.DATABASE_NAME)
            .addTypeConverter(ingredientsConverter)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase):RecipeDao = recipeDatabase.recipeDao()
}