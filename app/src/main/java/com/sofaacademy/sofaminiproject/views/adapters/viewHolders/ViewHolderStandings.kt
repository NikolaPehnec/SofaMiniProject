package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentStandingsRowBinding
import com.sofaacademy.sofaminiproject.model.StandingsRow
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked

class ViewHolderStandings(
    private val binding: TournamentStandingsRowBinding,
    private val listener: OnTeamClicked
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StandingsRow, position: Int) {
        binding.position.text = position.toString()
        binding.teamName.text = item.team?.name
        binding.plays.text = item.played?.toString()
        binding.wins.text = item.wins?.toString()
        binding.draws.text = item.draws?.toString()
        binding.losses.text = item.losses?.toString()
        binding.goals.text = item.scoresFor?.toString() + ":" + item.scoresAgainst?.toString()
        binding.pts.text = item.played?.toString()
        binding.root.setOnClickListener {
            listener.onTeamClicked(item.team!!.id)
        }
    }
}
