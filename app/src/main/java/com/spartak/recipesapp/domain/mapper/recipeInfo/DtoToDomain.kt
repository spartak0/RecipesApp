package com.spartak.recipesapp.domain.mapper.recipeInfo

import com.spartak.recipesapp.data.network.dto.IngredientDto
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.domain.model.Ingredient
import com.spartak.recipesapp.domain.model.RecipeInfo

fun RecipeInformationDto.toDomain() = with(this) {
    RecipeInfo(
        id = id ?: 0,
        title = title ?: "",
        image = image ?: "",
        instruction = instructions ?: "",
        ingredients = ingredients?.map(IngredientDto::toDomain) ?: listOf(),
    )
}

fun IngredientDto.toDomain() = with(this) {
    Ingredient(
        id = id,
        name = name,
        original = original,
        amount = amount,
        unit = unit,
    )
}