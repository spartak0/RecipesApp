package com.spartak.recipesapp.domain.mapper.recipe

import com.spartak.recipesapp.data.network.dto.RecipeDto
import com.spartak.recipesapp.domain.model.Recipe

fun Recipe.domainToDto() = with(this) {
    RecipeDto(id = id, title = name, image = image)
}