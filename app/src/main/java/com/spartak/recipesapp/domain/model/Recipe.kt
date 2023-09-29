package com.spartak.recipesapp.domain.model

data class Recipe(
    val id: Int,
    val name: String,
    val image: String,
    val isFavorite: Boolean,
)