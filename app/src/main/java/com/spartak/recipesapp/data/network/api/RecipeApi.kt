package com.spartak.recipesapp.data.network.api

import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.data.network.dto.RecipeResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET(RECIPE_ENDPOINT)
    fun getRecipes(
        @Query(INSTRUCTIONS_PARAMETER) instructionsRequired: Boolean = true,
        @Query(SORT_PARAMETER) sort: String = "",
        @Query(NUMBER_PARAMETER) number: Int = MAX_PAGE_SIZE_VALUE,
        @Query(OFFSET_PARAMETER) offset: Int = 0,
    ): Single<RecipeResponseDto>

    @GET(RECIPE_INFORMATION_ENDPOINT)
    fun getRecipeInformation(
        @Path(ID_PARAMETER) id: Int,
        @Query(INCLUDE_NUTRITION_PARAMETER) includeNutrition: Boolean = false,
    ): Single<RecipeInformationDto>

    @GET(SEARCH_RECIPE_ENDPOINT)
    fun searchRecipes(
        @Query(INSTRUCTIONS_PARAMETER) instructionsRequired: Boolean = true,
        @Query(TITLE_MATCH_PARAMETER) titleMatch: String = "",
        @Query(SORT_PARAMETER) sort: String = "",
        @Query(NUMBER_PARAMETER) number: Int = MAX_PAGE_SIZE_VALUE,
        @Query(OFFSET_PARAMETER) offset: Int = 0,
    ): Single<RecipeResponseDto>

    companion object {
        const val BASE_URL = "https://api.spoonacular.com/"
        const val API_KEY = "d40af269b2874f2bbf159b77c3ba7cb6"
        const val API_KEY_PARAMETER = "apiKey"
        const val MAX_PAGE_SIZE_VALUE: Int = 10
        private const val RECIPE_ENDPOINT = "recipes/complexSearch"
        private const val RECIPE_INFORMATION_ENDPOINT = "recipes/{id}/information"
        private const val SEARCH_RECIPE_ENDPOINT = "recipes/complexSearch"
        private const val INSTRUCTIONS_PARAMETER = "instructionsRequired"
        private const val INCLUDE_NUTRITION_PARAMETER = "includeNutrition"
        private const val ID_PARAMETER = "id"
        private const val SORT_PARAMETER = "sort"
        private const val NUMBER_PARAMETER = "number"
        private const val OFFSET_PARAMETER = "offset"
        private const val TITLE_MATCH_PARAMETER = "titleMatch"
    }
}