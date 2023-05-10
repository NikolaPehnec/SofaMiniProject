package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.ConcatAdapter
import com.sofaacademy.sofaminiproject.databinding.FragmentSportBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_ARG
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.yearFormat
import com.sofaacademy.sofaminiproject.viewmodel.SportEventViewModel
import com.sofaacademy.sofaminiproject.views.activities.EventDetailsActivity
import com.sofaacademy.sofaminiproject.views.activities.MainActivity
import com.sofaacademy.sofaminiproject.views.adapters.SportEventsArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.SportEventsHeaderAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class SportFragment : Fragment(), SportEventsArrayAdapter.OnItemClickListener {
    private var _binding: FragmentSportBinding? = null
    private val binding get() = _binding!!
    private var slug: String? = null
    private var currentDate: LocalDate? = null
    private val sportEventViewModel: SportEventViewModel by viewModels()
    private lateinit var sportEventsArrayAdapter: SportEventsArrayAdapter
    private lateinit var sportEventsHeaderAdapter: SportEventsHeaderAdapter

    companion object {
        @JvmStatic
        fun newInstance(slug: String) =
            SportFragment().apply {
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
        _binding = FragmentSportBinding.inflate(inflater, container, false)
        currentDate = (requireActivity() as MainActivity).getCurrentDate()

        sportEventsArrayAdapter = SportEventsArrayAdapter(requireContext(), mutableListOf(), this)
        sportEventsHeaderAdapter = SportEventsHeaderAdapter(requireContext(), currentDate, null)
        binding.eventsRv.adapter = ConcatAdapter(sportEventsHeaderAdapter, sportEventsArrayAdapter)
        setListeners()

        return binding.root
    }

    //Load kod prvog selektiranja trenutnog datuma
    fun reloadSportData(date: LocalDate) {
        currentDate = date
        sportEventsHeaderAdapter.setHeaderInfo(currentDate, null)
        sportEventViewModel.getSportEvents(slug!!, yearFormat.format(date))
    }

    private fun setListeners() {
        sportEventViewModel.sportEventsList.distinctUntilChanged().observe(viewLifecycleOwner) {
            it?.let {
                val res = it.groupBy { it.tournament }.flatMap {
                    listOf(it.key) + it.value.sortedBy { e ->
                        ZonedDateTime.parse(e.startDate!!, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    }
                }
                sportEventsArrayAdapter.setItems(res)
                sportEventsHeaderAdapter.setHeaderInfo(currentDate, res.size.toString())

                if (res.isEmpty()) {
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
                }
            }
        }
    }

    override fun onItemClick(item: Any) {
        when (item) {
            is SportEvent -> EventDetailsActivity.start(item, requireContext())
        }
    }
}
