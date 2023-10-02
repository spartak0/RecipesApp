package com.spartak.recipesapp.ui.home_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.app.appComponent
import com.spartak.recipesapp.databinding.FragmentHomeBinding
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections
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
        val recipeAdapter = RecipeAdapter() { recipeId ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipeId)
            findNavController().navigate(action)
        }
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
        viewModel.recipes.observe(viewLifecycleOwner) {
            recipeAdapter.update(it)
        }
    }

}