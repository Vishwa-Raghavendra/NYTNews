package com.nyt.nytnews.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nyt.nytnews.fragments.BookMarkedFragmentTop
import com.nyt.nytnews.fragments.TopTopStoriesFragment

class HomeFragmentPageAdapter(fragment:Fragment) : FragmentStateAdapter(fragment)
{
    companion object
    {
        const val NO_OF_FRAGMENTS = 2
    }

    override fun getItemCount() = NO_OF_FRAGMENTS


    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TopTopStoriesFragment()
            else -> BookMarkedFragmentTop()
        }
    }

}