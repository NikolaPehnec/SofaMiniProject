package com.sofaacademy.sofaminiproject.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sofaacademy.sofaminiproject.views.fragments.TeamDetailsFragment

class TeamDetailsPagerAdapter(private val teamID: Int, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TeamDetailsFragment.newInstance(teamID)
            1 -> TeamDetailsFragment.newInstance(teamID)
            2 -> TeamDetailsFragment.newInstance(teamID)
            3 -> TeamDetailsFragment.newInstance(teamID)
            else -> TeamDetailsFragment.newInstance(teamID)
        }
    }
}
