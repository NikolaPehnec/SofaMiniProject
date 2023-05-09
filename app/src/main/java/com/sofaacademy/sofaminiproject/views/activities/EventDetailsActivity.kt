package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.sofaacademy.sofaminiproject.databinding.ActivityEventDetailsBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.Constants.EVENT_ID_KEY
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getEventFromIntent
import com.sofaacademy.sofaminiproject.viewmodel.SportEventViewModel
import com.sofaacademy.sofaminiproject.views.adapters.IncidentsArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Prvi podaci preko intenta, onda dohvacanje svjezih s API-ja
 */
@AndroidEntryPoint
class EventDetailsActivity : AppCompatActivity() {

    private lateinit var sportEvent: SportEvent
    private lateinit var binding: ActivityEventDetailsBinding
    private val sportEventViewModel: SportEventViewModel by viewModels()
    private lateinit var incidentsArrayAdapter: IncidentsArrayAdapter

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
        setContentView(binding.root)

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
            println()
            println()
        }
    }

    private fun fillEventDetails(sportEvent: SportEvent) {
        binding.eventDetails.setEventInfo(sportEvent)
    }
}
