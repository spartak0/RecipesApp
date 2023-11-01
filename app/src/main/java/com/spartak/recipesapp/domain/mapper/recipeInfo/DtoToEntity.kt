package com.spartak.recipesapp.domain.mapper.recipeInfo

import com.spartak.recipesapp.data.database.entity.IngredientEntity
import com.spartak.recipesapp.data.database.entity.RecipeInfoEntity
import com.spartak.recipesapp.data.network.dto.IngredientDto
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto

fun RecipeInformationDto.toEntity() = with(this) {
    RecipeInfoEntity(
        id ?: 0,
        title ?: "",
        image ?: "",
        instructions ?: "",
        ingredients?.map(IngredientDto::toEntity) ?: emptyList()
    )
}

fun IngredientDto.toEntity() = with(this) {
    IngredientEntity(id, name, original, amount, unit)
}