package com.spartak.recipesapp.ui.home_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.app.appComponent
import com.spartak.recipesapp.databinding.FragmentHomeBinding
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections
import com.spartak.recipesapp.ui.recycler_utils.RecipeAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchRecipe(SortRecipes.Popularity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservables()
    }

    private fun setupUI() {
        val context = this.context
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
                when (checkedId) {
                    btnNew.id -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                    btnPopular.id -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                    btnTrending.id -> Toast.makeText(context, "0", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupObservables() {
        viewModel.recipes.observe(viewLifecycleOwner) {
            (binding.rvRecipes.adapter as RecipeAdapter).update(it)
        }
    }

}