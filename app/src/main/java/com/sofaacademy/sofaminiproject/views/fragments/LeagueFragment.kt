package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import com.sofaacademy.sofaminiproject.databinding.FragmentLeagueBinding
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_ARG
import com.sofaacademy.sofaminiproject.viewmodel.TournamentsViewModel
import com.sofaacademy.sofaminiproject.views.adapters.TournamentsArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeagueFragment : Fragment(), TournamentsArrayAdapter.OnItemClickListener {
    private var _binding: FragmentLeagueBinding? = null
    private val binding get() = _binding!!
    private var slug: String? = null
    private val tournamentsViewModel: TournamentsViewModel by viewModels()
    private lateinit var tournamentsArrayAdapter: TournamentsArrayAdapter

    companion object {
        @JvmStatic
        fun newInstance(slug: String) =
            LeagueFragment().apply {
                arguments = Bundle().apply {
                    putString(SLUG_ARG, slug)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            slug = it.getString(SLUG_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeagueBinding.inflate(inflater, container, false)

        tournamentsArrayAdapter = TournamentsArrayAdapter(requireContext(), mutableListOf(), this)
        binding.leaguesRv.adapter = tournamentsArrayAdapter
        setListeners()

        tournamentsViewModel.getTournaments(slug!!)
        return binding.root
    }

    private fun setListeners() {
        tournamentsViewModel.tournamentsList.distinctUntilChanged().observe(viewLifecycleOwner) {
            it?.let {
                tournamentsArrayAdapter.setItems(it)
                // Never empty state?
                /* if (res.isEmpty()) {
                     binding.eventsRv.visibility = View.GONE
                     binding.noDataAnimation.visibility = View.VISIBLE
                     binding.noDataMess.visibility = View.VISIBLE
                     binding.noDataAnimation.playAnimation()
                 } else {
                     binding.eventsRv.visibility = View.VISIBLE
                     binding.noDataAnimation.visibility = View.GONE
                     binding.noDataMess.visibility = View.GONE
                     binding.noDataAnimation.progress = 0f
                     binding.noDataAnimation.pauseAnimation()
                 }*/
            }
        }
    }

    override fun onItemClick(item: Any) {
    }
}
