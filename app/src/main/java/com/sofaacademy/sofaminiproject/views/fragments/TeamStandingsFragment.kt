package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamStandingsBinding
import com.sofaacademy.sofaminiproject.model.StandingsType
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.viewmodel.TournamentsViewModel
import com.sofaacademy.sofaminiproject.views.adapters.StandingsArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.StandingsHeaderArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamStandingsFragment : Fragment(), OnTeamClicked {
    private var _binding: FragmentTeamStandingsBinding? = null
    private val binding get() = _binding!!
    private var team: Team2? = null
    private val tournamentsViewModel: TournamentsViewModel by viewModels()
    private lateinit var headerAdapter: StandingsHeaderArrayAdapter
    private lateinit var arrayAdapter: StandingsArrayAdapter

    companion object {
        @JvmStatic
        fun newInstance(team: Team2) =
            TeamStandingsFragment().apply {
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
        _binding = FragmentTeamStandingsBinding.inflate(inflater, container, false)
        headerAdapter = StandingsHeaderArrayAdapter()
        arrayAdapter = StandingsArrayAdapter(mutableListOf(), this)
        binding.eventsRv.adapter = ConcatAdapter(headerAdapter, arrayAdapter)

        setListeners()
        tournamentsViewModel.getTournamentStandings("2")
        return binding.root
    }

    private fun setListeners() {
        tournamentsViewModel.tournamentStandings.observe(viewLifecycleOwner) {
            val totalStandings =
                it.filter { it.type == StandingsType.TOTAL.standingsType }.firstOrNull()
            totalStandings?.let {
                arrayAdapter.setItems(it.sortedStandingsRows!!)
            }
        }
    }

    override fun onTeamHomeClicked() {
    }

    override fun onTeamAwayClicked() {
    }

    override fun onTeamClicked(teamId: Int) {
    }
}
