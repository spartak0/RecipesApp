package com.spartak.recipesapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spartak.recipesapp.data.network.api.RecipeApi
import com.spartak.recipesapp.data.network.dto.RecipeDto
import com.spartak.recipesapp.domain.mapper.recipe.toDomain
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.SortRecipes
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RecipePagingSource @AssistedInject constructor(
    private val recipeApi: RecipeApi,
    @Assisted(SORT_RECIPE_PARAMETER) private val sortRecipes: SortRecipes,
) : PagingSource<Int, Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val offsetValue = pageNumber * RecipeApi.MAX_PAGE_SIZE_VALUE
            val pageSize = params.loadSize.coerceAtMost(RecipeApi.MAX_PAGE_SIZE_VALUE)
            val response = recipeApi.getRecipes(
                sort = sortRecipes.value,
                offset = offsetValue,
                number = pageSize
            )
            val recipes = response.blockingLast().recipes?.map(RecipeDto::toDomain) ?: listOf()
            val nextPageNumber = if (recipes.isEmpty()) null else pageNumber + 1
            val prevPageNumber =
                if (pageNumber > 1) pageNumber - 1 else null
            return LoadResult.Page(recipes, prevPageNumber, nextPageNumber)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted(SORT_RECIPE_PARAMETER) sortRecipes: SortRecipes): RecipePagingSource
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
        const val SORT_RECIPE_PARAMETER = "sortRecipes"
    }
}