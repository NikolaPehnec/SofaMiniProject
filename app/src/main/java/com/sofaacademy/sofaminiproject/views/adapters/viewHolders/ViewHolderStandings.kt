package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import android.content.Context
import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.TournamentStandingsRowBinding
import com.sofaacademy.sofaminiproject.model.StandingsRow
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked

class ViewHolderStandings(
    val context: Context,
    private val binding: TournamentStandingsRowBinding,
    private val listener: OnTeamClicked
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(highlightTeamId: Int?, item: StandingsRow, position: Int) {
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

        if (highlightTeamId == item.team?.id) {
            binding.root.setBackgroundColor(context.getColor(R.color.color_primary_highlight))
            binding.positionPlaceholder.backgroundTintList =
                ColorStateList.valueOf(context.getColor(R.color.surface_surface_1))
        } else {
            binding.root.setBackgroundColor(context.getColor(R.color.surface_surface_1))
            binding.positionPlaceholder.backgroundTintList =
                ColorStateList.valueOf(context.getColor(R.color.color_secondary_default))
        }
    }
}
