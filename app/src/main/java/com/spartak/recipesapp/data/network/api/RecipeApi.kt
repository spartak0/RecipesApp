package com.spartak.recipesapp.data.network.api

import com.spartak.recipesapp.data.network.dto.RecipeInformationDto
import com.spartak.recipesapp.data.network.dto.RecipeResponseDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET(RECIPE_ENDPOINT)
    fun getRecipes(
        @Query(INSTRUCTIONS_PARAMETER) instructionsRequired: Boolean = true,
        @Query(SORT_PARAMETER) sort: String = "",
    ): Observable<RecipeResponseDto>

    @GET(RECIPE_INFORMATION_ENDPOINT)
    fun getRecipeInformation(
        @Path(ID_PARAMETER) id: Int,
        @Query(INCLUDE_NUTRITION_PARAMETER) includeNutrition: Boolean = false,
    ): Observable<RecipeInformationDto>

    companion object {
        const val BASE_URL = "https://api.spoonacular.com/"
        const val API_KEY = "d40af269b2874f2bbf159b77c3ba7cb6"
        const val RECIPE_ENDPOINT = "recipes/complexSearch"
        const val RECIPE_INFORMATION_ENDPOINT = "recipes/{id}/information"
        const val API_KEY_PARAMETER = "apiKey"
        const val INSTRUCTIONS_PARAMETER = "instructionsRequired"
        const val INCLUDE_NUTRITION_PARAMETER = "includeNutrition"
        const val ID_PARAMETER = "id"
        const val SORT_PARAMETER = "sort"
    }
}