package com.spartak.recipesapp.ui.main_screen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterViewPager(
    fragment: Fragment,
    private val list: List<Fragment>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int) = list[position]
}