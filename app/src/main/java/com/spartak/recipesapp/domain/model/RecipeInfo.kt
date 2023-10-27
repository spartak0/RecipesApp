package com.spartak.recipesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeInfo(
    val id: Int,
    val title: String,
    val image: String,
    val ingredients: List<Ingredient>,
    val instruction: String,
) : Parcelable

@Parcelize
data class Ingredient(
    val id: Int,
    val name: String,
    val original: String,
    val amount: Double,
    val unit: String,
) : Parcelable