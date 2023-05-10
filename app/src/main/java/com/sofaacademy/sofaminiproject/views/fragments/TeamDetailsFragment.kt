package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sofaacademy.sofaminiproject.databinding.FragmentTeamDetailBinding
import com.sofaacademy.sofaminiproject.utils.Constants.TEAM_ID_ARG
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsFragment : Fragment() {
    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!
    private var teamId: Int? = null
    private val teamViewModel: TeamViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance(teamId: Int) =
            TeamDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(TEAM_ID_ARG, teamId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teamId = it.getInt(TEAM_ID_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)

        setListeners()

        //  teamViewModel.
        return binding.root
    }


    private fun setListeners() {
        //teamViewModel.
    }

}
