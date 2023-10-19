package com.spartak.recipesapp.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spartak.recipesapp.data.database.converter.IngredientsConverter
import com.spartak.recipesapp.data.database.dao.RecipeDao
import com.spartak.recipesapp.data.database.entity.RecipeEntity
import com.spartak.recipesapp.data.database.entity.RecipeInfoEntity

@TypeConverters(IngredientsConverter::class)
@Database(
    entities = [RecipeEntity::class, RecipeInfoEntity::class],
    version = RecipeDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        const val DATABASE_NAME = "recipe_database"
        const val DATABASE_VERSION = 1
    }
}