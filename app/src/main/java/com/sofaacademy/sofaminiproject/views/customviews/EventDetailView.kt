package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.EventDetailViewBinding
import com.sofaacademy.sofaminiproject.model.MatchStatus
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.elapsedMinutesFromDate
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getFormattedDetailDate
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getHourFromDate
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeamScoreColorBasedOnTimeAndResult
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked

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

    private var sportEvent: SportEvent? = null

    fun setEventInfo(
        sportEvent: SportEvent
    ) {
        this.sportEvent = sportEvent
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

        binding.teamHome.setTeamName(sportEvent.homeTeam.name)
        binding.teamAway.setTeamName(sportEvent.awayTeam.name)
        loadTeamLogos(sportEvent.homeTeam.id, sportEvent.awayTeam.id)
    }

    private fun loadTeamLogos(teamHomeId: Int, teamAwayId: Int) {
        binding.teamHome.loadTeamLogo(teamHomeId)
        binding.teamAway.loadTeamLogo(teamAwayId)
    }

    fun setOnItemClickListener(onTeamClicked: OnTeamClicked) {
        binding.teamHome.setOnClickListener {
            onTeamClicked.onTeamClicked(sportEvent!!.homeTeam)
        }
        binding.teamAway.setOnClickListener {
            onTeamClicked.onTeamClicked(sportEvent!!.awayTeam)
        }
    }
}
