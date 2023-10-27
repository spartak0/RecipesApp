package com.spartak.recipesapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class RecipeInformationDto(
    val id: Int?,
    val title: String?,
    val image: String?,
    val instructions: String?,
    @SerializedName(INGREDIENTS_NAME)
    val ingredients: List<IngredientDto>?
) {
    companion object {
        private const val INGREDIENTS_NAME = "extendedIngredients"
    }
}

data class IngredientDto(
    val id: Int,
    val name: String,
    val original: String,
    val amount: Double,
    val unit: String,
)
