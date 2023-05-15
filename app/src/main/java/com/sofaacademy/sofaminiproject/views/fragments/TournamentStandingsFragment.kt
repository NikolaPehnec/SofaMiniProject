package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.sofaacademy.sofaminiproject.databinding.FragmentTournamentStandingsBinding
import com.sofaacademy.sofaminiproject.model.StandingsType
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.TOURNAMENT_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTournament
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.viewmodel.TournamentsViewModel
import com.sofaacademy.sofaminiproject.views.activities.TeamDetailsActivity
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.StandingsArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.headerAdapters.StandingsHeaderArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentStandingsFragment : Fragment(), OnTeamClicked {
    private var _binding: FragmentTournamentStandingsBinding? = null
    private val binding get() = _binding!!
    private var tournament: Tournament? = null
    private val tournamentsViewModel: TournamentsViewModel by activityViewModels()
    private lateinit var standingsHeaderAdapter: StandingsHeaderArrayAdapter
    private lateinit var standingsArrayAdapter: StandingsArrayAdapter

    companion object {
        @JvmStatic
        fun newInstance(tournament: Tournament) =
            TournamentStandingsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TOURNAMENT_ARG, tournament)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tournament = it.getTournament()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTournamentStandingsBinding.inflate(inflater, container, false)
        standingsHeaderAdapter = StandingsHeaderArrayAdapter()
        standingsArrayAdapter = StandingsArrayAdapter(null, mutableListOf(), this)

        binding.standingsRv.adapter = ConcatAdapter(standingsHeaderAdapter, standingsArrayAdapter)
        setListeners()
        tournamentsViewModel.getTournamentStandings(tournament?.id.toString())
        return binding.root
    }

    private fun setListeners() {
        tournamentsViewModel.tournamentStandings.observe(viewLifecycleOwner) {
            val totalStandings =
                it.filter { it.type == StandingsType.TOTAL.standingsType }.firstOrNull()
            totalStandings?.let {
                standingsArrayAdapter.setItems(it.sortedStandingsRows!!)
            }
        }
    }

    override fun onTeamClicked(team: Team2) {
        TeamDetailsActivity.start(team, requireContext())
    }
}
