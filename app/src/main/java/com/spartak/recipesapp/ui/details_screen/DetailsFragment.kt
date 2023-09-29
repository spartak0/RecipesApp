package com.spartak.recipesapp.ui.details_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.FragmentDetailsBinding
import com.spartak.recipesapp.domain.model.RecipeInfo

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val binding: FragmentDetailsBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = args.recipeId
        val recipe = RecipeInfo(recipeId, "", "", listOf(), "")
        //todo fetch from vm
        with(binding) {
            tvIngredient.text = recipe.ingredients.joinToString { it.name }
            tvRecipe.text = recipe.instruction
            Glide.with(ivRecipe).load(recipe.image).into(ivRecipe)
        }
    }

}