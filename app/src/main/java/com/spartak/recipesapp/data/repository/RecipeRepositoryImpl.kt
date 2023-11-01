package com.spartak.recipesapp.data.repository

import com.spartak.recipesapp.data.database.dao.RecipeDao
import com.spartak.recipesapp.data.database.entity.RecipeEntity
import com.spartak.recipesapp.data.database.entity.RecipeInfoEntity
import com.spartak.recipesapp.data.network.api.RecipeApi
import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.data.paging.RecipePagingSource
import com.spartak.recipesapp.domain.mapper.recipe.toDomain
import com.spartak.recipesapp.domain.mapper.recipe.toEntity
import com.spartak.recipesapp.domain.mapper.recipeInfo.toDomain
import com.spartak.recipesapp.domain.mapper.recipeInfo.toEntity
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.domain.repository.RecipeRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val dao: RecipeDao,
    private val recipePagingSourceFactory: RecipePagingSource.Factory,
) : RecipeRepository {

    override fun getRecipes(sortRecipes: SortRecipes): RecipePagingSource =
        recipePagingSourceFactory.create(sortRecipes = sortRecipes)

    override fun searchRecipesInDB(title: String): Flowable<List<Recipe>> =
        dao.searchRecipes(title).map { it.map(RecipeEntity::toDomain) }

    override fun getRecipeInfo(id: Int): Single<RecipeInfo> =
        api.getRecipeInformation(id = id)
            .single(RecipeInformationDto(0, "", "", "", emptyList()))
            .map(RecipeInformationDto::toDomain)

    override fun getSyncRecipeInfo(id: Int): Single<RecipeInfo> =
        dao.existsRecipeInfoFavorite(id).flatMap {
            if (it) {
                dao.fetchRecipeInfoById(id)
                    .map(RecipeInfoEntity::toDomain)
            } else {
                api.getRecipeInformation(id)
                    .single(RecipeInformationDto(0, "", "", "", emptyList()))
                    .map { recipeInfoDto ->
                        dao.addRecipeInfo(recipeInfoDto.toEntity())
                        recipeInfoDto.toDomain()
                    }
            }
        }

    override fun getFavoriteRecipes(): Flowable<List<Recipe>> =
        dao.fetchRecipes().map { it.map(RecipeEntity::toDomain) }

    override fun addFavoriteRecipe(recipe: Recipe): Single<Unit> = dao.addRecipe(recipe.toEntity())
    override fun deleteFavoriteRecipe(id: Int): Observable<Unit> =
        Single.merge(
            dao.deleteRecipe(id),
            dao.deleteRecipeInfo(id)
        ).toObservable()

    override fun isFavoriteRecipe(recipeId: Int): Single<Boolean> = dao.existsFavorite(recipeId)
    override fun searchRecipes(title: String, sortRecipes: SortRecipes): RecipePagingSource =
        recipePagingSourceFactory.create(searchTitle = title, sortRecipes = sortRecipes)
}