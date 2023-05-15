package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamStandingsBinding
import com.sofaacademy.sofaminiproject.model.StandingsRow
import com.sofaacademy.sofaminiproject.model.StandingsType
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.viewmodel.TournamentsViewModel
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.StandingsArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.headerAdapters.StandingsHeaderArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.TournamentSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamStandingsFragment : Fragment(), OnTeamClicked {
    private var _binding: FragmentTeamStandingsBinding? = null
    private val binding get() = _binding!!
    private var team: Team2? = null
    private val tournamentsViewModel: TournamentsViewModel by viewModels()
    private val teamViewModel: TeamViewModel by activityViewModels()
    private val tournaments = mutableListOf<Tournament>()
    private lateinit var headerAdapter: StandingsHeaderArrayAdapter
    private lateinit var arrayAdapter: StandingsArrayAdapter
    private lateinit var spinnerArrayAdapter: TournamentSpinnerAdapter
    private val tournamentStandingsMap = mutableMapOf<Int, List<StandingsRow>>()

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
        arrayAdapter = StandingsArrayAdapter(team!!.id, mutableListOf(), this)
        spinnerArrayAdapter = TournamentSpinnerAdapter(mutableListOf(), requireContext())
        binding.eventsRv.adapter = ConcatAdapter(headerAdapter, arrayAdapter)
        binding.tournamentSpinner.adapter = spinnerArrayAdapter

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        tournamentsViewModel.tournamentStandings.observe(viewLifecycleOwner) {
            val totalStandings =
                it.filter { it.type == StandingsType.TOTAL.standingsType }.firstOrNull()
            totalStandings?.let {
                arrayAdapter.setItems(it.sortedStandingsRows!!)
            }

            tournamentStandingsMap[totalStandings!!.tournament!!.id] =
                totalStandings.sortedStandingsRows!!
        }

        teamViewModel.teamTournaments.observe(viewLifecycleOwner) {
            tournaments.clear()
            tournaments.addAll(it)
            spinnerArrayAdapter.setItems(it)
            binding.tournamentSpinner.setSelection(0)
        }

        binding.tournamentSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Dohvacanje spremljenih standingsa
                val item = tournaments[position]
                if (tournamentStandingsMap.containsKey(item.id)) {
                    arrayAdapter.setItems(tournamentStandingsMap[item.id]!!)
                } else {
                    tournamentsViewModel.getTournamentStandings(item.id.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onTeamClicked(teamId: Int) {
    }
}
