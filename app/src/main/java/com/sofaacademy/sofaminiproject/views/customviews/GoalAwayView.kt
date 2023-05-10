package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.GoalAwayRowBinding
import com.sofaacademy.sofaminiproject.model.GoalType
import com.sofaacademy.sofaminiproject.model.Incident
import com.sofaacademy.sofaminiproject.utils.Constants

class GoalAwayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: GoalAwayRowBinding = GoalAwayRowBinding.inflate(
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

    fun setGoalIncidentInfo(
        slug: String,
        goalIncident: Incident.GoalIncident
    ) {
        binding.scoreHome.text = goalIncident.homeScore?.toString()
        binding.scoreAway.text = goalIncident.awayScore?.toString()
        binding.playerName.text = goalIncident.player?.name
        binding.minute.text = goalIncident.time?.toString() + "'"
        binding.minuteBasketball.text = goalIncident.time?.toString() + "'"

        // Api vraca touchdown,fieldGoal,extraPoint, safety, safety ne znam koja ikona
        val imageDrawable = when (goalIncident.goalType) {
            GoalType.REGULAR.goalType -> R.drawable.icon_goal
            GoalType.TOUCHDOWN.goalType -> R.drawable.ic_touchdown
            GoalType.FIELD_GOAL.goalType -> R.drawable.ic_field_goal
            GoalType.EXTRA_POINT.goalType -> R.drawable.ic_extra_point
            GoalType.TWO_POINT.goalType -> R.drawable.ic_num_basketball_2
            GoalType.THREE_POINT.goalType -> R.drawable.ic_num_basketball_3
            else -> R.drawable.icon_goal
        }
        binding.goalImg.load(AppCompatResources.getDrawable(context, imageDrawable))

        when (slug) {
            Constants.SLUG_BASKETBALL -> {
                binding.minuteBasketball.visibility = View.VISIBLE
                binding.separatorH.visibility = View.VISIBLE
                binding.minute.visibility = View.GONE
                binding.playerName.visibility = View.GONE
            }

            else -> {
                binding.minute.visibility = View.VISIBLE
                binding.playerName.visibility = View.GONE
                binding.minuteBasketball.visibility = View.GONE
                binding.separatorH.visibility = View.GONE
            }
        }
    }
}
