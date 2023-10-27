package com.spartak.recipesapp.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spartak.recipesapp.data.database.entity.IngredientEntity

@ProvidedTypeConverter
class IngredientsConverter {
    @TypeConverter
    fun fromIngredients(value: List<IngredientEntity>): String = Gson().toJson(value)

    @TypeConverter
    fun toIngredients(value: String): List<IngredientEntity> =
        Gson().fromJson(value, object : TypeToken<List<IngredientEntity>>() {}.type)
}