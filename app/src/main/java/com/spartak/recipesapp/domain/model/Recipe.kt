package com.spartak.recipesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val author: String,
    val name: String,
    val image: String,
    val ingredients: List<String>,
    val recipe: String,
) : Parcelable