package com.spartak.recipesapp.domain.repository

import com.spartak.recipesapp.data.paging.RecipePagingSource
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.model.SortRecipes
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface RecipeRepository {
    fun getRecipes(sortRecipes: SortRecipes): RecipePagingSource
    fun searchRecipesInDB(title: String): Flowable<List<Recipe>>
    fun searchRecipes(title: String, sortRecipes: SortRecipes): RecipePagingSource
    fun getRecipeInfo(id: Int): Observable<RecipeInfo>
    fun getFavoriteRecipes(): Flowable<List<Recipe>>
    fun getFavoriteRecipesInfo(id: Int): Single<RecipeInfo>
    fun addFavoriteRecipe(recipe: Recipe): Single<Unit>
    fun deleteFavoriteRecipe(recipe: Recipe): Single<Unit>
    fun isFavoriteRecipe(recipeId: Int): Single<Boolean>
}