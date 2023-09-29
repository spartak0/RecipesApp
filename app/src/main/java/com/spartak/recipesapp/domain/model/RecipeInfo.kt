package com.spartak.recipesapp.domain.model

data class RecipeInfo(
    val id: Int,
    val title:String,
    val image: String,
    val ingredients: List<Ingredient>,
    val instruction: String,
    )

data class Ingredient(
    val id: Int,
    val name: String,
    val original: String,
    val image: String,
    val amount: Double,
    val unit: String,
)