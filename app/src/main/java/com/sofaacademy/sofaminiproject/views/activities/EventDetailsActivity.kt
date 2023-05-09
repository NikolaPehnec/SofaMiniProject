package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.sofaacademy.sofaminiproject.databinding.ActivityEventDetailsBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.Constants.EVENT_ID_KEY
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getEventFromIntent

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var sportEvent: SportEvent
    private lateinit var binding: ActivityEventDetailsBinding

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
    }

    private fun fillEventDetails(sportEvent: SportEvent) {
        binding.eventDetails.setEventInfo(sportEvent)
    }
}
