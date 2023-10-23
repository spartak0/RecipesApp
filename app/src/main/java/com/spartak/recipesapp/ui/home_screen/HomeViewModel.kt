package com.spartak.recipesapp.ui.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.domain.repository.RecipeRepository
import com.spartak.recipesapp.ui.DisposableViewModel
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : DisposableViewModel() {

    private val _sortRecipes: MutableLiveData<SortRecipes> = MutableLiveData(SortRecipes.NONE)
    private val sortRecipes: LiveData<SortRecipes> = _sortRecipes

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading

    private val _favoriteRecipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val favoriteRecipes: LiveData<List<Recipe>> = _favoriteRecipes

    val recipes: LiveData<PagingData<Recipe>> =
        sortRecipes.switchMap {
            newPager(it).liveData
        }
            .cachedIn(this)

    init {
        subscribeFavoriteRecipes()
    }

    private fun subscribeFavoriteRecipes() {
        recipeRepository.getFavoriteRecipes()
            .applySchedulers(onNext = {
                _favoriteRecipes.value = it
            })
    }

    private fun newPager(sort: SortRecipes): Pager<Int, Recipe> {
        return Pager(config = PagingConfig(PREFETCH_PAGE_SIZE, enablePlaceholders = false)) {
            recipeRepository.getRecipes(sort)
        }
    }

    fun setSortRecipes(sortRecipes: SortRecipes) {
        _sortRecipes.postValue(sortRecipes)
    }

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    fun addRecipeInDb(recipe: Recipe) {
        recipeRepository.addFavoriteRecipe(recipe).applySchedulers(onSuccess = {}, onError = {})
    }

    fun deleteRecipeInDb(recipe: Recipe) {
        recipeRepository.deleteFavoriteRecipe(recipe).applySchedulers(onSuccess = {}, onError = {})
    }

    fun existRecipe(id: Int, onSuccess: (Boolean) -> Unit, onError: (Throwable) -> Unit) =
        recipeRepository.isFavoriteRecipe(id)
            .applySchedulers(
                onSuccess = onSuccess,
                onError = onError,
            )

    companion object {
        private const val PREFETCH_PAGE_SIZE = 5
    }
}