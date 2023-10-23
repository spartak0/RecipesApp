package com.spartak.recipesapp.domain.mapper.recipeInfo

import com.spartak.recipesapp.data.database.entity.IngredientEntity
import com.spartak.recipesapp.data.database.entity.RecipeInfoEntity
import com.spartak.recipesapp.domain.model.Ingredient
import com.spartak.recipesapp.domain.model.RecipeInfo

fun RecipeInfo.toEntity() = with(this) {
    RecipeInfoEntity(
        id = id,
        title = title,
        image = image,
        ingredients = ingredients.map(Ingredient::toEntity),
        instruction = instruction,
    )
}

fun Ingredient.toEntity() = with(this) {
    IngredientEntity(
        id = id,
        name = name,
        original = original,
        amount = amount,
        unit = unit,
    )
}

