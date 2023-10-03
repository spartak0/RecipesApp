package com.spartak.recipesapp.ui.details_screen

import com.spartak.recipesapp.domain.model.Ingredient
import com.spartak.recipesapp.utils.withoutPostfix

fun Iterable<Ingredient>.joinStringColumn() = joinToString(separator = ",\n", postfix = ".") {
    "${it.name} - ${it.amount.withoutPostfix()} ${it.unit}"
}