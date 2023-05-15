package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.CardIncidentRowBinding
import com.sofaacademy.sofaminiproject.model.CardTeamSide
import com.sofaacademy.sofaminiproject.model.Incident
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.listeners.OnIncidentClicked

class ViewHolderCardIncident(
    private val binding: CardIncidentRowBinding,
    val sportEvent: SportEvent
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        cardIncident: Incident.CardIncident,
        onIncidentClicked: OnIncidentClicked
    ) {

        when (cardIncident.teamSide) {
            CardTeamSide.AWAY.team -> {
                binding.goalHome.visibility = View.GONE
                binding.goalAway.visibility = View.VISIBLE
                binding.goalAway.setCardIncidentInfo(cardIncident)
                binding.root.setOnClickListener {
                    onIncidentClicked.onIncidentClicked(cardIncident.player!!, sportEvent.awayTeam)
                }
            }

            CardTeamSide.HOME.team -> {
                binding.goalHome.visibility = View.VISIBLE
                binding.goalAway.visibility = View.GONE
                binding.goalHome.setCardIncidentInfo(cardIncident)
                binding.root.setOnClickListener {
                    onIncidentClicked.onIncidentClicked(cardIncident.player!!, sportEvent.homeTeam)
                }
            }
        }
    }
}
