package com.spartak.recipesapp.utils

fun Double.withoutPostfix(): String =
    if (this % 1 == 0.0) this.toInt().toString() else this.toString()