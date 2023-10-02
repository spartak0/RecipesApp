@file:JvmName("DomainToDtoKt")

package com.spartak.recipesapp.domain.mapper.recipeInfo

import com.spartak.recipesapp.data.network.dto.IngredientDto
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.domain.model.Ingredient
import com.spartak.recipesapp.domain.model.RecipeInfo

fun RecipeInfo.toDto() = with(this) {
    RecipeInformationDto(
        id = id,
        title = title,
        ingredients = ingredients.map(Ingredient::toDto),
        image = image,
        instructions = instructions,
    )
}

fun Ingredient.toDto() = with(this) {
    IngredientDto(
        id = id,
        name = name,
        original = original,
        image = image,
        amount = amount,
        unit = unit,
    )
}