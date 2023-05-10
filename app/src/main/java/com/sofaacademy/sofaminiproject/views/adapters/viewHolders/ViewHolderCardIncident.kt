package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.CardIncidentRowBinding
import com.sofaacademy.sofaminiproject.databinding.GoalIncidentRowBinding
import com.sofaacademy.sofaminiproject.model.CardTeamSide
import com.sofaacademy.sofaminiproject.model.GoalScoringTeam
import com.sofaacademy.sofaminiproject.model.Incident

class ViewHolderCardIncident(private val binding: CardIncidentRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(cardIncident: Incident.CardIncident) {
        when (cardIncident.teamSide) {
            CardTeamSide.AWAY.team -> {
                binding.goalHome.visibility = View.GONE
                binding.goalAway.visibility = View.VISIBLE
                binding.goalAway.setCardIncidentInfo(cardIncident)
            }

            CardTeamSide.HOME.team -> {
                binding.goalHome.visibility = View.VISIBLE
                binding.goalAway.visibility = View.GONE
                binding.goalHome.setCardIncidentInfo(cardIncident)
            }
        }
    }
}