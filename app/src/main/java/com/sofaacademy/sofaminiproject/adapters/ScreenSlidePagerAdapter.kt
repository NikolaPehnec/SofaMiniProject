package com.sofaacademy.sofaminiproject.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_AMERICAN_FOOTBALL
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_BASKETBALL
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_FOOTBALL
import com.sofaacademy.sofaminiproject.views.fragments.SlugFragment

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SlugFragment.newInstance(SLUG_FOOTBALL)
            1 -> SlugFragment.newInstance(SLUG_BASKETBALL)
            2 -> SlugFragment.newInstance(SLUG_AMERICAN_FOOTBALL)
            else -> SlugFragment.newInstance(SLUG_FOOTBALL)
        }
    }
}
