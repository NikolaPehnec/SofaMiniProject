package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.EventDetailViewBinding
import com.sofaacademy.sofaminiproject.model.MatchStatus
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.Constants.BASE_TEAM_URL
import com.sofaacademy.sofaminiproject.utils.Constants.IMG_ENDPOINT
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.elapsedMinutesFromDate
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getFormattedDetailDate
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getHourFromDate
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeamScoreColorBasedOnTimeAndResult

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
        val colorHome =
            getTeamScoreColorBasedOnTimeAndResult(
                sportEvent.status,
                sportEvent.homeScore,
                sportEvent.awayScore,
                context
            )
        val colorAway =
            getTeamScoreColorBasedOnTimeAndResult(
                sportEvent.status,
                sportEvent.awayScore,
                sportEvent.homeScore,
                context
            )

        when (sportEvent.status) {
            MatchStatus.NOT_STARTED.status -> {
                binding.scoreHome.visibility = View.GONE
                binding.scoreAway.visibility = View.GONE
                binding.scoreHome.visibility = View.GONE
                binding.scoreSlash.visibility = View.GONE
                binding.matchStatus.visibility = View.GONE

                sportEvent.startDate?.let {
                    binding.eventDate.apply {
                        visibility = View.VISIBLE
                        text = getFormattedDetailDate(it)
                    }
                    binding.eventTime.apply {
                        visibility = View.VISIBLE
                        text = getHourFromDate(it)
                    }
                }
            }

            MatchStatus.IN_PROGRESS.status -> {
                binding.eventDate.visibility = View.GONE
                binding.eventTime.visibility = View.GONE
                binding.scoreHome.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorHome)
                    text = sportEvent.homeScore?.total.toString()
                }
                binding.scoreAway.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorAway)
                    text = sportEvent.awayScore?.total.toString()
                }
                binding.scoreSlash.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorHome)
                }
                binding.matchStatus.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorHome)
                    text = elapsedMinutesFromDate(sportEvent.startDate ?: "")
                }
            }

            MatchStatus.FINISHED.status -> {
                binding.eventDate.visibility = View.GONE
                binding.eventTime.visibility = View.GONE
                binding.scoreHome.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorHome)
                    text = sportEvent.homeScore?.total.toString()
                }
                binding.scoreAway.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorAway)
                    text = sportEvent.awayScore?.total.toString()
                }
                binding.scoreSlash.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorHome)
                }
                binding.matchStatus.apply {
                    visibility = View.VISIBLE
                    setTextColor(colorHome)
                    text = context.getString(R.string.match_status_finished)
                }
            }
        }

        binding.teamHomeName.text = sportEvent.homeTeam.name
        binding.teamAwayName.text = sportEvent.awayTeam.name
        loadTeamLogos(sportEvent.homeTeam.id, sportEvent.awayTeam.id)
    }

    private fun loadTeamLogos(teamHomeId: Int, teamAwayId: Int) {
        binding.teamHomeLogo.load("$BASE_TEAM_URL$teamHomeId$IMG_ENDPOINT")
        binding.teamAwayLogo.load("$BASE_TEAM_URL$teamAwayId$IMG_ENDPOINT")
    }
}
