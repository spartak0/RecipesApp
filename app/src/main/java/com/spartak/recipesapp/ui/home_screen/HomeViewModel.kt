package com.spartak.recipesapp.ui.home_screen

import androidx.lifecycle.MutableLiveData
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.domain.repository.RecipeRepository
import com.spartak.recipesapp.ui.DisposableViewModel
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : DisposableViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>(listOf())
    val recipes = _recipes

    fun fetchRecipe(sortRecipes: SortRecipes) {
        recipeRepository.getRecipes(sortRecipes)
            .applySchedulers(
                { _recipes.value = it },
                Throwable::printStackTrace
            )
    }

}