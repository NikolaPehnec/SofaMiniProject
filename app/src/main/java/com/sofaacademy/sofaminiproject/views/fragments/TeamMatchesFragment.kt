package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamMatchesBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.sortedByDateDesc
import com.sofaacademy.sofaminiproject.utils.listeners.OnEventClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.views.adapters.TeamSportEventsArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable


@AndroidEntryPoint
class TeamMatchesFragment : Fragment(), OnTournamentClicked, OnEventClicked {
    private var _binding: FragmentTeamMatchesBinding? = null
    private lateinit var teamSportEventsArrayAdapter: TeamSportEventsArrayAdapter
    private val binding get() = _binding!!
    private var team: Team2? = null
    private val teamViewModel: TeamViewModel by viewModels()

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
        teamSportEventsArrayAdapter =
            TeamSportEventsArrayAdapter(mutableListOf(), this, this)
        binding.eventsRv.adapter = teamSportEventsArrayAdapter

        setListeners()
        teamViewModel.getEvents(team?.id.toString(), Constants.NEXT, "0", true)
        return binding.root
    }

    private fun setListeners() {
        teamViewModel.teamEvents.observe(viewLifecycleOwner) { sportEvents ->
            val res1 = sportEvents.sortedByDateDesc()

            var finished = mutableListOf<Serializable>()

            //Specific grouping and ordering as seen in sofascore
            for (i in res1.indices) {
                if (i == 0) {
                    finished.add(res1[i].tournament)
                    finished.add(res1[i])
                } else {
                    if (res1[i - 1].tournament == res1[i].tournament) {
                        finished.add(res1[i])
                    } else {
                        finished.add(res1[i].tournament)
                        finished.add(res1[i])
                    }
                }
            }

            teamSportEventsArrayAdapter.setItems(finished)
            val nextEventPosition = teamSportEventsArrayAdapter.findNextEventPosition()
            binding.eventsRv.scrollToPosition(nextEventPosition)
        }
    }

    override fun onEventClicked(sportEvent: SportEvent) {
    }

    override fun onTournamentClicked(tournamet: Tournament) {
    }
}
