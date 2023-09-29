package com.spartak.recipesapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class RecipeResponseDto(
    val offset: Int,
    val number: Int,
    @SerializedName(RESULTS_NAME)
    val recipes: List<RecipeDto>,
){
    companion object{
        private const val RESULTS_NAME = "results"
    }
}

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
)