package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamSquadBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_PLAYER
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeam
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.views.activities.PlayerDetailsActivity
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.TeamPlayersArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.headerAdapters.SquadHeaderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamSquadFragment : Fragment(), OnPlayerClicked {
    private var _binding: FragmentTeamSquadBinding? = null
    private val binding get() = _binding!!
    private var team: Team2? = null
    private val teamViewModel: TeamViewModel by activityViewModels()
    private lateinit var teamPlayersArrayAdapter: TeamPlayersArrayAdapter
    private lateinit var teamCoachArrayAdapter: TeamPlayersArrayAdapter
    private lateinit var coachHeaderAdapter: SquadHeaderAdapter
    private lateinit var playersHeaderAdapter: SquadHeaderAdapter

    companion object {
        @JvmStatic
        fun newInstance(team: Team2) =
            TeamSquadFragment().apply {
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
        _binding = FragmentTeamSquadBinding.inflate(inflater, container, false)
        setListeners()

        teamPlayersArrayAdapter =
            TeamPlayersArrayAdapter(mutableListOf(), this)
        // Nema manager entiteta s apija, bolje bi bilo jedan adapter i tip Coach, Player
        teamCoachArrayAdapter =
            TeamPlayersArrayAdapter(mutableListOf(), this)

        val headerCoach = getString(R.string.coach_header)
        val headerPlayers = getString(R.string.players_header)
        coachHeaderAdapter = SquadHeaderAdapter(headerCoach)
        playersHeaderAdapter = SquadHeaderAdapter(headerPlayers)
        binding.squadRv.adapter =
            ConcatAdapter(
                coachHeaderAdapter,
                teamCoachArrayAdapter,
                playersHeaderAdapter,
                teamPlayersArrayAdapter
            )

        return binding.root
    }

    private fun setListeners() {
        teamViewModel.teamPlayers.observe(viewLifecycleOwner) {
            teamPlayersArrayAdapter.setItems(it)
        }
        teamViewModel.teamDetails.observe(viewLifecycleOwner) {
            teamCoachArrayAdapter.setItems(
                mutableListOf(
                    Player(
                        -1,
                        it!!.managerName,
                        null,
                        it.country,
                        null,
                        null,
                        TYPE_PLAYER,
                        false
                    )
                )
            )
        }
    }

    override fun onPlayerClicked(player: Player) {
        PlayerDetailsActivity.start(player, team, requireContext())
    }
}
