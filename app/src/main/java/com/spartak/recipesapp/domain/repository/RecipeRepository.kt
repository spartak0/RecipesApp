package com.spartak.recipesapp.domain.repository

import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.RecipeInfo
import io.reactivex.rxjava3.core.Observable

interface RecipeRepository {
    fun getRecipes(): Observable<List<Recipe>>
    fun getRecipeInfo(id:Int): Observable<RecipeInfo>
}