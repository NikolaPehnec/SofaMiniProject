package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sofaacademy.sofaminiproject.databinding.FragmentTournamentMatchesBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.TOURNAMENT_ARG
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTournament
import com.sofaacademy.sofaminiproject.utils.listeners.OnEventClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.viewmodel.TournamentsViewModel
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.EventPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TournamentMatchesFragment : Fragment(), OnTournamentClicked, OnEventClicked {
    private var _binding: FragmentTournamentMatchesBinding? = null
    private val binding get() = _binding!!
    private var tournament: Tournament? = null
    private val tournamentsViewModel: TournamentsViewModel by activityViewModels()
    private lateinit var pagerAdapter: EventPagingAdapter

    companion object {
        @JvmStatic
        fun newInstance(tournament: Tournament) =
            TournamentMatchesFragment().apply {
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
        _binding = FragmentTournamentMatchesBinding.inflate(inflater, container, false)
        pagerAdapter = EventPagingAdapter(this, this)
        binding.eventsRv.adapter = pagerAdapter

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        lifecycleScope.launch {
            tournamentsViewModel.getAllTournamentEvents(tournament?.id.toString()).observe(viewLifecycleOwner) {
                it?.let {
                    pagerAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

    override fun onEventClicked(sportEvent: SportEvent) {
    }

    override fun onTournamentClicked(tournamet: Tournament) {
    }
}
