package com.spartak.recipesapp.ui.home_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.domain.repository.RecipeRepository
import com.spartak.recipesapp.ui.DisposableViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : DisposableViewModel() {

    private val _sortRecipes: MutableLiveData<SortRecipes> = MutableLiveData(SortRecipes.NONE)
    val sortRecipes = _sortRecipes

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipes: Flow<PagingData<Recipe>> = sortRecipes.asFlow()
        .flatMapLatest { newPager(it).flow }
        .cachedIn(viewModelScope)

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

    companion object {
        private const val PREFETCH_PAGE_SIZE = 5
    }
}