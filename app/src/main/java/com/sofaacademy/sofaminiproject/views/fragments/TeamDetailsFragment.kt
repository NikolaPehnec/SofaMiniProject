package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamDetailBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getForeignPlayersPercentIndicator
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.utils.helpers.FlagHelper
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.views.activities.EventDetailsActivity
import com.sofaacademy.sofaminiproject.views.activities.TournamentDetailsActivity
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.TeamTournamentsArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TeamDetailsFragment : Fragment(), OnTournamentClicked {
    private var _binding: FragmentTeamDetailBinding? = null
    private lateinit var teamTournamentsArrayAdapter: TeamTournamentsArrayAdapter
    private val binding get() = _binding!!
    private var team: Team2? = null
    private var nextSportEvent: SportEvent? = null
    private val teamViewModel: TeamViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance(team: Team2) =
            TeamDetailsFragment().apply {
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
        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        teamTournamentsArrayAdapter =
            TeamTournamentsArrayAdapter(mutableListOf(), this)
        binding.tournamentsRv.adapter = teamTournamentsArrayAdapter
        binding.tournamentsRv.layoutManager = GridLayoutManager(requireContext(), 3)

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        teamViewModel.teamDetails.observe(viewLifecycleOwner) {
            fillTeamDetails(it)
        }
        teamViewModel.teamPlayers.observe(viewLifecycleOwner) {
            fillPlayersInfo(it)
        }
        teamViewModel.nextTeamEvents.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                nextSportEvent = it.sortedBy { e ->
                    ZonedDateTime.parse(e.startDate!!, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                }.first()

                fillNextEventInfo(nextSportEvent!!)
            }
        }
        teamViewModel.teamTournaments.observe(viewLifecycleOwner) {
            teamTournamentsArrayAdapter.setItems(it)
        }

        binding.nextMatchEvent.setOnClickListener {
            nextSportEvent?.let {
                EventDetailsActivity.start(it, requireContext())
            }
        }
        binding.nextMatchTournament.setOnClickListener {
            nextSportEvent?.let {
                TournamentDetailsActivity.start(it.tournament, requireContext())
            }
        }
    }

    private fun fillTeamDetails(team: Team2) {
        binding.coachRow.coachName.text = team.managerName.toString()
        binding.coachRow.countryLogo.load(
            FlagHelper.getFlagBitmap(
                requireContext(),
                team.country.name
            )
        )
        binding.coachRow.countryName.text = team.country.name
        binding.venue.text = team.venue
    }

    private fun fillPlayersInfo(players: List<Player>) {
        val totalPlayersNum = players.size
        val foreignPlayersNum = players.filter { p -> p.country?.name == team!!.country.name }.size

        binding.totalPlayers.playerNumber.text = totalPlayersNum.toString()
        binding.foreignPlayers.foreignPlayersNumber.text = foreignPlayersNum.toString()
        binding.foreignPlayers.teamForeignPercentIndicator.progress =
            getForeignPlayersPercentIndicator(totalPlayersNum, foreignPlayersNum)
    }

    private fun fillNextEventInfo(event: SportEvent) {
        binding.nextMatchTournament.setTournamentInfo(event.tournament)
        binding.nextMatchEvent.setMatchInfo(event)
        binding.nextMatchEvent.setMatchDateTime(event)
    }

    override fun onTournamentClicked(tournamet: Tournament) {
        TournamentDetailsActivity.start(tournamet, requireContext())
    }
}
