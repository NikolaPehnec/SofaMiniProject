package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.MatchViewBinding
import com.sofaacademy.sofaminiproject.model.Score
import com.sofaacademy.sofaminiproject.utils.Constants.BASE_TEAM_URL
import com.sofaacademy.sofaminiproject.utils.Constants.IMG_ENDPOINT

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

    fun setMatchInfo(
        matchTime: String,
        currentTime: String,
        teamHome: String,
        teamAway: String,
        homeScore: Score?,
        awayScore: Score?
    ) {
        binding.timeStart.text = matchTime
        binding.currentTime.text = currentTime
        binding.teamHome.text = teamHome
        binding.teamAway.text = teamAway
        binding.teamHomeResult.text = homeScore?.total?.toString()
        binding.teamAwayResult.text = awayScore?.total?.toString()
    }

    fun loadTeamLogos(teamHomeId: Int, teamAwayId: Int) {
        binding.teamHomeLogo.load("$BASE_TEAM_URL$teamHomeId$IMG_ENDPOINT")
        binding.teamAwayLogo.load("$BASE_TEAM_URL$teamAwayId$IMG_ENDPOINT")
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
