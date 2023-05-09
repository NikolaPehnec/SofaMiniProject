package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.EventDetailViewBinding
import com.sofaacademy.sofaminiproject.model.MatchStatus
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.Constants.BASE_TEAM_URL
import com.sofaacademy.sofaminiproject.utils.Constants.IMG_ENDPOINT
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getFormattedDetailDate
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getHourFromDate

class EventDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: EventDetailViewBinding = EventDetailViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EventDetail, 0, 0).apply {
            try {
                binding.eventDate.text = getText(R.styleable.EventDetail_eventDate)
                binding.eventTime.text = getText(R.styleable.EventDetail_eventTime)
                binding.teamHomeName.text = getText(R.styleable.EventDetail_teamHomeName)
                binding.teamAwayName.text = getText(R.styleable.EventDetail_teamAwayName)
            } finally {
                recycle()
            }
        }
    }

    fun setEventInfo(
        sportEvent: SportEvent
    ) {
        when (sportEvent.status) {
            MatchStatus.NOT_STARTED.status -> {
                sportEvent.startDate?.let {
                    binding.eventDate.text = getFormattedDetailDate(it)
                    binding.eventTime.text = getHourFromDate(it)
                }
            }

            MatchStatus.IN_PROGRESS.status -> {}
            MatchStatus.FINISHED.status -> {}
        }
        binding.teamHomeName.text = sportEvent.homeTeam.name
        binding.teamAwayName.text = sportEvent.awayTeam.name
        loadTeamLogos(sportEvent.homeTeam.id, sportEvent.awayTeam.id)
    }

    private fun loadTeamLogos(teamHomeId: Int, teamAwayId: Int) {
        binding.teamHomeLogo.load("$BASE_TEAM_URL$teamHomeId$IMG_ENDPOINT")
        binding.teamAwayLogo.load("$BASE_TEAM_URL$teamAwayId$IMG_ENDPOINT")
    }

    fun setHomeTeamColor(color: Int) {
        // binding.teamHome.setTextColor(color)
    }

    fun setHomeTeamScoreColor(color: Int) {
        //  binding.teamHomeResult.setTextColor(color)
    }

    fun setAwayTeamScoreColor(color: Int) {
        //   binding.teamAwayResult.setTextColor(color)
    }

    fun setAwayTeamColor(color: Int) {
        //   binding.teamAway.setTextColor(color)
    }

    fun setCurrentTimeColor(color: Int) {
        //   binding.currentTime.setTextColor(color)
    }
}
