package com.spartak.recipesapp.domain.repository

import com.spartak.recipesapp.data.paging.RecipePagingSource
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.model.SortRecipes
import io.reactivex.rxjava3.core.Observable

interface RecipeRepository {
    fun getRecipes(sortRecipes: SortRecipes): RecipePagingSource
    fun getRecipeInfo(id: Int): Observable<RecipeInfo>
}