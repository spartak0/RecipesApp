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
    val sortRecipes = _sortRecipes

    val recipes: LiveData<PagingData<Recipe>> =
        sortRecipes.switchMap {
            newPager(it).liveData
        }.cachedIn(this)

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading

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

    companion object {
        private const val PREFETCH_PAGE_SIZE = 5
    }
}