package com.spartak.recipesapp.data.network.repository

import com.spartak.recipesapp.data.network.api.RecipeApi
import com.spartak.recipesapp.data.network.dto.RecipeDto
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.domain.mapper.recipe.toDomain
import com.spartak.recipesapp.domain.mapper.recipeInfo.toDomain
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.repository.RecipeRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi
) : RecipeRepository {
    override fun getRecipes(): Observable<List<Recipe>> =
        api.getRecipes().map { recipeResponseDto ->
            recipeResponseDto.recipes?.map(RecipeDto::toDomain) ?: listOf()
        }

    override fun getRecipeInfo(id: Int): Observable<RecipeInfo> =
        api.getRecipeInformation(id = id).map(RecipeInformationDto::toDomain)
}