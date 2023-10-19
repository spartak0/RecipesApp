package com.spartak.recipesapp.domain.mapper.recipeInfo

import com.spartak.recipesapp.data.database.entity.IngredientEntity
import com.spartak.recipesapp.data.database.entity.RecipeInfoEntity
import com.spartak.recipesapp.domain.mapper.recipe.toDomain
import com.spartak.recipesapp.domain.model.RecipeInfo

fun RecipeInfoEntity.toDomain() = with(this) {
    RecipeInfo(
        id = id,
        title = title,
        image = image,
        ingredients = ingredients.map(IngredientEntity::toDomain),
        instruction = instruction,
    )
}
