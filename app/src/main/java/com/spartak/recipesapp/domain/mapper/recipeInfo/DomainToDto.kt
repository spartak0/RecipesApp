package com.spartak.recipesapp.domain.mapper.recipeInfo

import com.spartak.recipesapp.data.network.dto.IngredientDto
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.domain.model.Ingredient
import com.spartak.recipesapp.domain.model.RecipeInfo

fun RecipeInformationDto.toDomain() = with(this) {
    RecipeInfo(
        id = id,
        title = title,
        image = image,
        instruction = instruction,
        ingredients = ingredients.map(IngredientDto::toDomain),
    )
}

fun IngredientDto.toDomain() = with(this) {
    Ingredient(
        id = id,
        name = name,
        original = original,
        image = image,
        amount = amount,
        unit = unit,
    )
}