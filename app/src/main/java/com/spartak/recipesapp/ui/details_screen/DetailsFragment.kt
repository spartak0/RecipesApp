package com.spartak.recipesapp.ui.details_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.spartak.recipesapp.R
import com.spartak.recipesapp.app.appComponent
import com.spartak.recipesapp.databinding.FragmentDetailsBinding
import com.spartak.recipesapp.utils.withoutPostfix
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val binding: FragmentDetailsBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailsViewModel by viewModels() {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        viewModel.fetchRecipeInfo(args.recipeId)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.recipeInfo.observe(viewLifecycleOwner) { recipeInfo ->
            with(binding) {
                tvIngredient.text =
                    recipeInfo.ingredients.joinToString(separator = ",\n", postfix = ".") { "${it.name} - ${it.amount.withoutPostfix()} ${it.unit}" }
                tvInstructions.text = recipeInfo.instructions
                Glide.with(ivRecipe).load(recipeInfo.image).into(ivRecipe)
            }
        }
    }

}