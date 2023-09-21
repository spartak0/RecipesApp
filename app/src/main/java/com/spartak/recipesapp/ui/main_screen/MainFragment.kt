package com.spartak.recipesapp.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.FragmentMainBinding
import com.spartak.recipesapp.ui.home_screen.HomeFragment
import com.spartak.recipesapp.ui.saved_screen.SavedFragment

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val fragmentList = listOf(HomeFragment(), SavedFragment())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterViewPager = AdapterViewPager(this, fragmentList)
        with(binding.bottomNavigationViewPager) {
            adapter = adapterViewPager
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
        binding.bottomNavigation.setOnItemSelectedListener {
            binding.bottomNavigationViewPager.currentItem = when (it.itemId) {
                R.id.homeFragment -> 0
                R.id.savedFragment -> 1
                else -> 0
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}