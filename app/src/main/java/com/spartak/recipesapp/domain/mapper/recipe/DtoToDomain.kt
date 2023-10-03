package com.spartak.recipesapp.domain.mapper.recipe

import com.spartak.recipesapp.data.network.dto.RecipeDto
import com.spartak.recipesapp.domain.model.Recipe

fun RecipeDto.toDomain() = with(this) {
    Recipe(id = id, name = title, image = image, isFavorite = false)
}