package com.spartak.recipesapp.ui.favorite_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.spartak.recipesapp.R
import com.spartak.recipesapp.app.appComponent
import com.spartak.recipesapp.databinding.FragmentSavedBinding
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections
import com.spartak.recipesapp.ui.recycler_utils.standart.RecipeAdapter
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_saved) {
    private val binding: FragmentSavedBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FavoriteViewModel by viewModels() {
        viewModelFactory
    }

    private val recipePagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecipeAdapter(recipeItemOnClick = { recipeId ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipeId)
            action.fromFavorite = true
            findNavController().navigate(action)
        }, isFavoriteOnClick = { recipe, view ->
            isFavoriteOnClick(recipe, view as ImageView)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObservable()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            etSearch.addTextChangedListener {
                if (it.toString().isBlank()) viewModel.fetchFavoriteRecipes()
                else viewModel.fetchSearchRecipes(it.toString())
            }
        }
    }

    private fun setupObservable() {
        with(binding) {
            viewModel.favoriteRecipes.observe(viewLifecycleOwner) {
                (rvRecipes.adapter as RecipeAdapter).update(it)
            }
        }

    }

    private fun setupUi() {
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipePagingAdapter
        }
    }

    private fun isFavoriteOnClick(recipe: Recipe, view: ImageView) {
        viewModel.existRecipe(
            recipe.id,
            onSuccess = {
                when (it) {
                    false -> {
                        viewModel.addRecipeInDb(
                            recipe = recipe.copy(isFavorite = true),
                            onError = { throwable ->
                                createSnackbar(
                                    throwable.localizedMessage ?: ""
                                )
                            })
                        view.setImageResource(R.drawable.save_filled)
                    }

                    true -> {
                        viewModel.deleteRecipeInDb(
                            recipeId = recipe.id,
                            onError = { throwable ->
                                createSnackbar(
                                    throwable.localizedMessage ?: ""
                                )
                            })
                        view.setImageResource(R.drawable.save)
                    }
                }
            },
            onError = {},
        )
    }

    private fun createSnackbar(text: String) {
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }
}