package com.sofaacademy.sofaminiproject.views.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.views.fragments.TeamDetailsFragment
import com.sofaacademy.sofaminiproject.views.fragments.TeamMatchesFragment
import com.sofaacademy.sofaminiproject.views.fragments.TeamSquadFragment
import com.sofaacademy.sofaminiproject.views.fragments.TeamStandingsFragment

class TeamDetailsPagerAdapter(private val team: Team2, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TeamDetailsFragment.newInstance(team)
            1 -> TeamMatchesFragment.newInstance(team)
            2 -> TeamStandingsFragment.newInstance(team)
            3 -> TeamSquadFragment.newInstance(team)
            else -> TeamDetailsFragment.newInstance(team)
        }
    }
}
