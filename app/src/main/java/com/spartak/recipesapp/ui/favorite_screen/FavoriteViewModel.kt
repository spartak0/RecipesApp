package com.spartak.recipesapp.ui.favorite_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.repository.RecipeRepository
import com.spartak.recipesapp.ui.DisposableViewModel
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : DisposableViewModel() {

    private val _favoriteRecipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val favoriteRecipes: LiveData<List<Recipe>> = _favoriteRecipes

    init {
        fetchFavoriteRecipes()
    }

    private fun fetchFavoriteRecipes() {
        recipeRepository.getFavoriteRecipes()
            .applySchedulers(onNext = {
                _favoriteRecipes.value = it
            })
    }

    fun existRecipe(id: Int, onSuccess: (Boolean) -> Unit, onError: (Throwable) -> Unit) =
        recipeRepository.isFavoriteRecipe(id)
            .applySchedulers(
                onSuccess = onSuccess,
                onError = onError,
            )

    fun addRecipeInDb(recipe: Recipe) {
        recipeRepository.addFavoriteRecipe(recipe).applySchedulers(onSuccess = {}, onError = {})
    }

    fun deleteRecipeInDb(recipe: Recipe) {
        recipeRepository.deleteFavoriteRecipe(recipe).applySchedulers(onSuccess = {}, onError = {})
    }
}