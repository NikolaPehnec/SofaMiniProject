package com.sofaacademy.sofaminiproject.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_AMERICAN_FOOTBALL
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_BASKETBALL
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_FOOTBALL
import com.sofaacademy.sofaminiproject.views.fragments.SportFragment

class SportPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SportFragment.newInstance(SLUG_FOOTBALL)
            1 -> SportFragment.newInstance(SLUG_BASKETBALL)
            2 -> SportFragment.newInstance(SLUG_AMERICAN_FOOTBALL)
            else -> SportFragment.newInstance(SLUG_FOOTBALL)
        }
    }
}
