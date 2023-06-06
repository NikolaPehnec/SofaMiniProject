package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.MatchRowBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers
import com.sofaacademy.sofaminiproject.utils.listeners.OnEventClicked

class ViewHolderEvent(
    private val binding: MatchRowBinding,
    private val listener: OnEventClicked
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SportEvent, nextItem: Any?, showEventDate: Boolean) {
        binding.matchItem.apply {
            setHomeTeamColor(
                EventHelpers.getTeamColorBasedOnTimeAndResult(
                    item.status,
                    item.homeScore,
                    item.awayScore,
                    context
                )
            )
            setAwayTeamColor(
                EventHelpers.getTeamColorBasedOnTimeAndResult(
                    item.status,
                    item.awayScore,
                    item.homeScore,
                    context
                )
            )
            setHomeTeamScoreColor(
                EventHelpers.getTeamScoreColorBasedOnTimeAndResult(
                    item.status,
                    item.homeScore,
                    item.awayScore,
                    context
                )
            )
            setAwayTeamScoreColor(
                EventHelpers.getTeamScoreColorBasedOnTimeAndResult(
                    item.status,
                    item.awayScore,
                    item.homeScore,
                    context
                )
            )
            setCurrentTimeColor(EventHelpers.getCurrentStatusColor(item.status, context))
            setMatchInfo(item)
            if (showEventDate) {
                setMatchDateTime(item)
            }
        }
        binding.separatorImg.visibility = View.GONE
        nextItem?.let {
            if (it is Tournament) {
                binding.separatorImg.visibility = View.VISIBLE
            }
        }

        binding.matchItem.setOnClickListener {
            listener.onEventClicked(item)
        }
    }
}
