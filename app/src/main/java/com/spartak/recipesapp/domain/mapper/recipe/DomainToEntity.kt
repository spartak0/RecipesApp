package com.spartak.recipesapp.domain.mapper.recipe

import com.spartak.recipesapp.data.database.entity.RecipeEntity
import com.spartak.recipesapp.domain.model.Recipe

fun Recipe.toEntity() = with(this) {
    RecipeEntity(id = id, title = name, image = image, isFavorite = isFavorite)
}