package com.spartak.recipesapp.data.network.repository

import com.spartak.recipesapp.data.network.api.RecipeApi
import com.spartak.recipesapp.data.network.dto.RecipeDto
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.domain.mapper.recipe.dtoToDomain
import com.spartak.recipesapp.domain.mapper.recipeInfo.toDomain
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.repository.RecipeRepository
import io.reactivex.rxjava3.core.Observable

class RecipeRepositoryImpl(
    private val api: RecipeApi
) : RecipeRepository {
    override fun getRecipes(): Observable<List<Recipe>> =
        api.getRecipes().map { list ->
            list.recipes.map(RecipeDto::dtoToDomain)
        }

    override fun getRecipeInfo(id: Int): Observable<RecipeInfo> =
        api.getRecipeInformation(id = id).map(RecipeInformationDto::toDomain)
}