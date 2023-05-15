package com.sofaacademy.sofaminiproject.views.adapters.pagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.views.fragments.TournamentMatchesFragment
import com.sofaacademy.sofaminiproject.views.fragments.TournamentStandingsFragment

class TournamentDetailsPagerAdapter(private val tournament: Tournament, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TournamentMatchesFragment.newInstance(tournament)
            1 -> TournamentStandingsFragment.newInstance(tournament)
            else -> TournamentMatchesFragment.newInstance(tournament)
        }
    }
}
