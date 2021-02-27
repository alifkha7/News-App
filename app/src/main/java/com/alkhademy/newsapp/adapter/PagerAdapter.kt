package com.alkhademy.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alkhademy.newsapp.fragment.BusinessFragment
import com.alkhademy.newsapp.fragment.SportsFragment
import com.alkhademy.newsapp.fragment.TechnologyFragment
import com.alkhademy.newsapp.fragment.TopHeadlinesFragment

class PagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        TopHeadlinesFragment(),
        BusinessFragment(),
        SportsFragment(),
        TechnologyFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "Top Headlines"
            1 -> "Business"
            2 -> "Sport"
            else -> "Technology"
        }
    }
}