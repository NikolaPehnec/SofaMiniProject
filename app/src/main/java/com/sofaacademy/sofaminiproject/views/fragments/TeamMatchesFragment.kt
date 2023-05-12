package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamMatchesBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.utils.listeners.OnEventClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.views.adapters.EventPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TeamMatchesFragment : Fragment(), OnTournamentClicked, OnEventClicked {
    private var _binding: FragmentTeamMatchesBinding? = null
    private val binding get() = _binding!!
    private var team: Team2? = null
    private val teamViewModel: TeamViewModel by viewModels()
    private lateinit var pagerAdapter: EventPagingAdapter

    companion object {
        @JvmStatic
        fun newInstance(team: Team2) =
            TeamMatchesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TEAM_ID_ARG, team)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            team = it.getTeam()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamMatchesBinding.inflate(inflater, container, false)
        pagerAdapter = EventPagingAdapter(this, this)
        binding.eventsRv.adapter = pagerAdapter

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        lifecycleScope.launch {
            teamViewModel.getAllTeamEvents(team?.id.toString()).observe(viewLifecycleOwner) {
                it?.let {
                    pagerAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

    override fun onEventClicked(sportEvent: SportEvent) {
    }

    override fun onTournamentClicked(tournamet: Tournament) {
    }
}
