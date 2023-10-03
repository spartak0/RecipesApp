package com.spartak.recipesapp.domain.repository

import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.model.SortRecipes
import io.reactivex.rxjava3.core.Observable

interface RecipeRepository {
    fun getRecipes(sortRecipes: SortRecipes): Observable<List<Recipe>>
    fun getRecipeInfo(id:Int): Observable<RecipeInfo>
}