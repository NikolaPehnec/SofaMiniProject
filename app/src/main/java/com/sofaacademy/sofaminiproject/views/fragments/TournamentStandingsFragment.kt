package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sofaacademy.sofaminiproject.databinding.FragmentTournamentStandingsBinding
import com.sofaacademy.sofaminiproject.model.StandingsRow
import com.sofaacademy.sofaminiproject.model.StandingsType
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.TOURNAMENT_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTournament
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.viewmodel.TournamentsViewModel
import com.sofaacademy.sofaminiproject.views.adapters.StandingsArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.StandingsHeaderArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.TournamentSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentStandingsFragment : Fragment(), OnTeamClicked {
    private var _binding: FragmentTournamentStandingsBinding? = null
    private val binding get() = _binding!!
    private var tournament: Tournament? = null
    private val tournamentsViewModel: TournamentsViewModel by viewModels()
    private val teamViewModel: TeamViewModel by activityViewModels()
    private val tournaments = mutableListOf<Tournament>()
    private lateinit var headerAdapter: StandingsHeaderArrayAdapter
    private lateinit var arrayAdapter: StandingsArrayAdapter
    private lateinit var spinnerArrayAdapter: TournamentSpinnerAdapter
    private val tournamentStandingsMap = mutableMapOf<Int, List<StandingsRow>>()

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
//        headerAdapter = StandingsHeaderArrayAdapter()
//        arrayAdapter = StandingsArrayAdapter(team!!.id,mutableListOf(), this)
//        spinnerArrayAdapter = TournamentSpinnerAdapter(mutableListOf(), requireContext())
//        binding.eventsRv.adapter = ConcatAdapter(headerAdapter, arrayAdapter)
//        binding.tournamentSpinner.adapter = spinnerArrayAdapter

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
    }

    override fun onTeamHomeClicked() {
    }

    override fun onTeamAwayClicked() {
    }

    override fun onTeamClicked(teamId: Int) {
    }
}
