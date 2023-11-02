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

    fun fetchFavoriteRecipes() {
        recipeRepository.getFavoriteRecipes()
            .applySchedulers(onNext = {
                _favoriteRecipes.value = it
            })
    }

    fun fetchSearchRecipes(text: String) {
        recipeRepository.searchRecipesInDB(text)
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

    fun addRecipeInDb(recipe: Recipe, onError: (Throwable) -> Unit) {
        recipeRepository.addFavoriteRecipe(recipe)
            .applySchedulers(onComplete = {}, onError = onError)
    }

    fun deleteRecipeInDb(recipeId: Int, onError: (Throwable) -> Unit) {
        recipeRepository.deleteFavoriteRecipe(recipeId)
            .applySchedulers(onComplete = {}, onError = onError)
    }

}