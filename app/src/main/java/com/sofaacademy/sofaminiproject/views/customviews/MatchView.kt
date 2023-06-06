package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.MatchViewBinding
import com.sofaacademy.sofaminiproject.model.MatchStatus
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadTeamImg
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers

class MatchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: MatchViewBinding = MatchViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.Tournament, 0, 0).apply {
            try {
                binding.currentTime.text = getString(R.styleable.Match_currentTime)
                binding.timeStart.text = getString(R.styleable.Match_matchTime)
                binding.teamAway.text = getString(R.styleable.Match_teamAway)
                binding.teamHome.text = getString(R.styleable.Match_teamHome)
            } finally {
                recycle()
            }
        }
    }

    fun setMatchInfo(event: SportEvent) {
        binding.teamHomeLogo.loadTeamImg(event.homeTeam.id.toString())
        binding.teamAwayLogo.loadTeamImg(event.awayTeam.id.toString())

        val startTime = UtilityFunctions.getHourFromDate(event.startDate)
        val matchCurrentStatus = EventHelpers.getCurrentMatchStatus(event.status, event.startDate)
        binding.timeStart.text = startTime
        binding.currentTime.text = matchCurrentStatus
        binding.teamHome.text = event.homeTeam.name
        binding.teamAway.text = event.awayTeam.name
        binding.teamHomeResult.text = event.homeScore.total?.toString()
        binding.teamAwayResult.text = event.awayScore.total?.toString()
    }

    fun setMatchDateTime(event: SportEvent) {
        val startTime = UtilityFunctions.getHourFromDate(event.startDate)
        val date = UtilityFunctions.getFormattedDetailDate(event.startDate, context)
        binding.timeStart.text = date

        if (event.status == MatchStatus.FINISHED.status) {
            binding.currentTime.text = context.getString(R.string.full_time)
        } else {
            binding.currentTime.text = startTime
        }
    }

    fun setHomeTeamColor(color: Int) {
        binding.teamHome.setTextColor(color)
    }

    fun setHomeTeamScoreColor(color: Int) {
        binding.teamHomeResult.setTextColor(color)
    }

    fun setAwayTeamScoreColor(color: Int) {
        binding.teamAwayResult.setTextColor(color)
    }

    fun setAwayTeamColor(color: Int) {
        binding.teamAway.setTextColor(color)
    }

    fun setCurrentTimeColor(color: Int) {
        binding.currentTime.setTextColor(color)
    }
}
