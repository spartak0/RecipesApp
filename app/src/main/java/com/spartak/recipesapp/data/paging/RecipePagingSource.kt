package com.spartak.recipesapp.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.spartak.recipesapp.data.database.dao.RecipeDao
import com.spartak.recipesapp.data.network.api.RecipeApi
import com.spartak.recipesapp.data.network.dto.RecipeResponseDto
import com.spartak.recipesapp.domain.mapper.recipe.toDomain
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.SortRecipes
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RecipePagingSource @AssistedInject constructor(
    private val recipeApi: RecipeApi,
    private val recipeDao: RecipeDao,
    @Assisted(SORT_RECIPE_PARAMETER) private val sortRecipes: SortRecipes,
) : RxPagingSource<Int, Recipe>() {

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Recipe>> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        val offsetValue = pageNumber * RecipeApi.MAX_PAGE_SIZE_VALUE
        val pageSize = params.loadSize.coerceAtMost(RecipeApi.MAX_PAGE_SIZE_VALUE)
        val response = recipeApi.getRecipes(
            sort = sortRecipes.value, offset = offsetValue, number = pageSize
        )

        return response.single(RecipeResponseDto()).subscribeOn(Schedulers.io())
            .map { recipeResponseDto ->
                recipeResponseDto.recipes?.map {
                    if (recipeDao.existsFavorite(it.id).blockingGet()) {
                        it.toDomain().copy(isFavorite = true)
                    } else it.toDomain()
                } ?: listOf()
            }.map { toLoadResult(it, pageNumber) }.onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(recipes: List<Recipe>, pageNumber: Int): LoadResult<Int, Recipe> {
        val nextPageNumber = if (recipes.isEmpty()) null else pageNumber + 1
        val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
        return LoadResult.Page(recipes, prevPageNumber, nextPageNumber)
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
