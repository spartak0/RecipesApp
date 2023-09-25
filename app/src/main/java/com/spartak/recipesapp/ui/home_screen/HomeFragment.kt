package com.spartak.recipesapp.ui.home_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.FragmentHomeBinding
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RecipeAdapter(
                listOf(
                    Recipe(
                        0,
                        "Spartal",
                        "Cace",
                        "https://i.stack.imgur.com/GsDIl.jpg",
                        listOf("1", "3", "4"),
                        "Aboba"
                    ),
                    Recipe(
                        3,
                        "Spartav",
                        "Cace3",
                        "https://i.stack.imgur.com/GsDIl.jpg",
                        listOf("1", "2", "3"),
                        "Aboba2"
                    ),
                )
            ) { recipe ->
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipe)
                findNavController().navigate(action)
            }
        }

    }

}