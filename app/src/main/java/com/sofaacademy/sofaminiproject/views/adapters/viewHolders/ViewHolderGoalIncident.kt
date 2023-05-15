package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.GoalIncidentRowBinding
import com.sofaacademy.sofaminiproject.model.GoalScoringTeam
import com.sofaacademy.sofaminiproject.model.Incident
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.listeners.OnIncidentClicked

class ViewHolderGoalIncident(
    private val binding: GoalIncidentRowBinding,
    val slug: String,
    val sportEvent: SportEvent
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(goalIncident: Incident.GoalIncident, onIncidentClicked: OnIncidentClicked) {
        when (goalIncident.scoringTeam) {
            GoalScoringTeam.AWAY.team -> {
                binding.goalHome.visibility = View.GONE
                binding.goalAway.visibility = View.VISIBLE
                binding.goalAway.setGoalIncidentInfo(slug, goalIncident)
                binding.root.setOnClickListener {
                    onIncidentClicked.onIncidentClicked(goalIncident.player!!, sportEvent.awayTeam)
                }
            }

            GoalScoringTeam.HOME.team -> {
                binding.goalHome.visibility = View.VISIBLE
                binding.goalAway.visibility = View.GONE
                binding.goalHome.setGoalIncidentInfo(slug, goalIncident)
                binding.root.setOnClickListener {
                    onIncidentClicked.onIncidentClicked(goalIncident.player!!, sportEvent.homeTeam)
                }
            }
        }
    }
}
