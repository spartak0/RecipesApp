package com.spartak.recipesapp.data.network.repository

import com.spartak.recipesapp.data.network.api.RecipeApi
import com.spartak.recipesapp.data.network.dto.RecipeDto
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.data.paging.RecipePagingSource
import com.spartak.recipesapp.domain.mapper.recipe.toDomain
import com.spartak.recipesapp.domain.mapper.recipeInfo.toDomain
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.domain.repository.RecipeRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val recipePagingSourceFactory: RecipePagingSource.Factory,
) : RecipeRepository {
    override fun getRecipes(
        sortRecipes: SortRecipes,
        offset: Int,
        number: Int,
    ): Observable<List<Recipe>> =
        api.getRecipes(sort = sortRecipes.value, offset = offset, number = number)
            .map { recipeResponseDto ->
                recipeResponseDto.recipes?.map(RecipeDto::toDomain) ?: listOf()
            }

    override fun getRecipes(sortRecipes: SortRecipes): RecipePagingSource =
        recipePagingSourceFactory.create(sortRecipes)

    override fun getRecipeInfo(id: Int): Observable<RecipeInfo> =
        api.getRecipeInformation(id = id).map(RecipeInformationDto::toDomain)
}