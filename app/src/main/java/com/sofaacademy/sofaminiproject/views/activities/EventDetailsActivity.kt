package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivityEventDetailsBinding
import com.sofaacademy.sofaminiproject.databinding.EventDetailToolbarBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.Constants.EVENT_ID_KEY
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getEventFromIntent
import com.sofaacademy.sofaminiproject.viewmodel.SportEventViewModel
import com.sofaacademy.sofaminiproject.views.adapters.IncidentsArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Prvi podaci preko intenta, onda dohvacanje svjezih s API-ja
 */
@AndroidEntryPoint
class EventDetailsActivity : AppCompatActivity(), IncidentsArrayAdapter.OnItemClickListener {

    private lateinit var sportEvent: SportEvent
    private lateinit var binding: ActivityEventDetailsBinding
    private val sportEventViewModel: SportEventViewModel by viewModels()
    private lateinit var incidentsArrayAdapter: IncidentsArrayAdapter
    private lateinit var customToolbarBinding: EventDetailToolbarBinding

    companion object {
        fun start(event: SportEvent, context: Context) {
            val intent = Intent(context, EventDetailsActivity::class.java).apply {
                putExtra(EVENT_ID_KEY, event)
            }
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        customToolbarBinding = EventDetailToolbarBinding.inflate(layoutInflater)
        binding.activityToolbar.addView(customToolbarBinding.root)

        incidentsArrayAdapter = IncidentsArrayAdapter(this, mutableListOf(), this)
        binding.incidentsRv.adapter = incidentsArrayAdapter

        sportEvent = getEventFromIntent(intent)
        fillEventDetails(sportEvent)
        sportEventViewModel.getEventDetails(sportEvent.id.toString())
        sportEventViewModel.getEventIncidents(sportEvent.id.toString())

        setListeners()
    }

    private fun setListeners() {
        sportEventViewModel.eventDetails.observe(this) {
            fillEventDetails(it)
        }
        sportEventViewModel.incidentsList.observe(this) {
            incidentsArrayAdapter.setItems(it.asReversed())
        }
    }

    private fun fillEventDetails(sportEvent: SportEvent) {
        binding.eventDetails.setEventInfo(sportEvent)
        customToolbarBinding.eventImg.load(
            "${Constants.BASE_TOURNAMENT_URL}${sportEvent.tournament.id}${Constants.IMG_ENDPOINT}"
        )
        customToolbarBinding.eventDesc.text =
            getString(
                R.string.event_details,
                sportEvent.tournament.sport.name,
                sportEvent.tournament.country.name,
                sportEvent.tournament.name,
                sportEvent.round.toString()
            )
    }

    override fun onItemClick(item: Any) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
