package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamMatchesBinding
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.views.adapters.TeamSportEventsArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamMatchesFragment : Fragment(), TeamSportEventsArrayAdapter.OnItemClickListener {
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
            TeamSportEventsArrayAdapter(requireContext(), mutableListOf(), this)
        binding.eventsRv.adapter = teamSportEventsArrayAdapter

        setListeners()
        teamViewModel.getEvents(team?.id.toString(), Constants.NEXT, "0")
        return binding.root
    }

    private fun setListeners() {
        teamViewModel.teamEvents.observe(viewLifecycleOwner) {
            teamSportEventsArrayAdapter.setItems(it)

            /*  if (it.isNotEmpty()) {
                  fillNextEventInfo(it.sortedBy { e ->
                      ZonedDateTime.parse(e.startDate!!, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                  }.first())
              }*/
        }
    }

    override fun onItemClick(item: Any) {
    }
}
