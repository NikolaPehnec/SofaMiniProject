package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.GoalAwayRowBinding
import com.sofaacademy.sofaminiproject.databinding.GoalHomeRowBinding
import com.sofaacademy.sofaminiproject.model.GoalScoringTeam
import com.sofaacademy.sofaminiproject.model.Incident

class GoalAwayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: GoalHomeRowBinding = GoalHomeRowBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GoalView, 0, 0).apply {
            try {
                binding.scoreHome.text = getString(R.styleable.GoalView_score_home)
                binding.scoreAway.text = getString(R.styleable.GoalView_score_away)
                binding.playerName.text = getString(R.styleable.GoalView_player_name)
                binding.minute.text = getString(R.styleable.GoalView_minute)
                binding.argument.text = getString(R.styleable.GoalView_argument)
            } finally {
                recycle()
            }
        }
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GoalView, 0, 0).apply {
            try {
                binding.scoreHome.text = getString(R.styleable.GoalView_score_home)
                binding.scoreAway.text = getString(R.styleable.GoalView_score_away)
                binding.playerName.text = getString(R.styleable.GoalView_player_name)
                binding.minute.text = getString(R.styleable.GoalView_minute)
                binding.argument.text = getString(R.styleable.GoalView_argument)
            } finally {
                recycle()
            }
        }
    }

    fun setGoalIncidentInfo(
        goalIncident: Incident.GoalIncident
    ) {
        binding.scoreHome.text = goalIncident.homeScore?.toString()
        binding.scoreAway.text = goalIncident.awayScore?.toString()
        binding.playerName.text = goalIncident.player?.name
        binding.minute.text = goalIncident.time?.toString() + "'"
    }
}
