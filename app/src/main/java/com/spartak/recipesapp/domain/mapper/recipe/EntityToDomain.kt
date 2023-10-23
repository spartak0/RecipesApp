package com.spartak.recipesapp.domain.mapper.recipe

import com.spartak.recipesapp.data.database.entity.IngredientEntity
import com.spartak.recipesapp.data.database.entity.RecipeEntity
import com.spartak.recipesapp.domain.model.Ingredient
import com.spartak.recipesapp.domain.model.Recipe

fun RecipeEntity.toDomain() = with(this) {
    Recipe(
        id = id,
        name = title,
        image = image,
        isFavorite = isFavorite,
    )
}
fun IngredientEntity.toDomain() = with(this) {
    Ingredient(
        id = id,
        amount = amount,
        name = name,
        original = original,
        unit = unit,
    )
}