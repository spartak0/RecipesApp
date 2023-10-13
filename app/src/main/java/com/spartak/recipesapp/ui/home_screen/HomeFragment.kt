package com.spartak.recipesapp.ui.home_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.app.appComponent
import com.spartak.recipesapp.databinding.FragmentHomeBinding
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections
import com.spartak.recipesapp.ui.recycler_utils.RecipeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels() {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservables()
    }

    private fun setupUI() {
        val recipeAdapter = RecipeAdapter() { recipeId ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipeId)
            findNavController().navigate(action)
        }
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
        with(binding) {
            btnGroupCategory.setOnCheckedChangeListener { _, checkedId ->
                viewModel.setSortRecipes(getSortRecipesByBtnId(checkedId))
            }
        }
    }

    private fun setupObservables() {
        with(binding) {
            lifecycleScope.launch {
                viewModel.recipes.collectLatest {
                    (rvRecipes.adapter as RecipeAdapter).submitData(it)
                }
            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                pbRecipes.isVisible = it
                rvRecipes.isVisible = !it
            }
        }

    }

    private fun getSortRecipesByBtnId(checkedId: Int) =
        with(binding) {
            when (checkedId) {
                btnNew.id -> SortRecipes.NONE
                btnPopular.id -> SortRecipes.Popularity
                btnRandom.id -> SortRecipes.Random
                else -> SortRecipes.NONE
            }
        }

}