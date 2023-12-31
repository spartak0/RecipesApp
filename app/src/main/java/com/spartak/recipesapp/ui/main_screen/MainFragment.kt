package com.spartak.recipesapp.ui.main_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.FragmentMainBinding
import com.spartak.recipesapp.ui.home_screen.HomeFragment
import com.spartak.recipesapp.ui.favorite_screen.FavoriteFragment

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
    private val fragmentList = listOf(HomeFragment(), FavoriteFragment())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            bottomNavigation.setOnItemSelectedListener {
                binding.container.currentItem = when (it.itemId) {
                    R.id.homeFragment -> 0
                    R.id.savedFragment -> 1
                    else -> 0
                }
                true
            }
            container.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.bottomNavigation.selectedItemId = when (position) {
                        0 -> R.id.homeFragment
                        1 -> R.id.savedFragment
                        else -> R.id.homeFragment
                    }
                    super.onPageSelected(position)
                }
            })
        }
    }

    private fun setupUi() {
        val viewPagerAdapter = AdapterViewPager(this, fragmentList)
        with(binding) {
            container.adapter = viewPagerAdapter
        }
    }

}