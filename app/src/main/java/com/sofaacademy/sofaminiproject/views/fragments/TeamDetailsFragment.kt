package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamDetailBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.utils.helpers.FlagHelper
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@AndroidEntryPoint
class TeamDetailsFragment : Fragment() {
    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!
    private var team: Team2? = null
    private val teamViewModel: TeamViewModel by viewModels()

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

        setListeners()
        teamViewModel.getAllTeamDetails(team?.id.toString())
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
                fillNextEventInfo(it.sortedBy { e ->
                    ZonedDateTime.parse(e.startDate!!, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                }.first())
            }
        }
        teamViewModel.teamTournaments.observe(viewLifecycleOwner){

        }
    }

    private fun fillTeamDetails(team: Team2) {
        // binding.coachRow.managerImg.load()
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
        val foreignPlayersNum = players.filter { p -> p.country.name == team!!.country.name }.size

        binding.totalPlayers.playerNumber.text = totalPlayersNum.toString()
        binding.foreignPlayers.foreignPlayersNumber.text = foreignPlayersNum.toString()
        binding.foreignPlayers.teamForeignPercentIndicator.progress =
            ((foreignPlayersNum * 1f / totalPlayersNum) * 100).roundToInt()
    }

    private fun fillNextEventInfo(event: SportEvent) {
        binding.nextMatchTournament.setTournamentInfo(event.tournament)
        binding.nextMatchEvent.setMatchInfo(event)
        binding.nextMatchEvent.setNextMatchDateTime(event)
    }

}
