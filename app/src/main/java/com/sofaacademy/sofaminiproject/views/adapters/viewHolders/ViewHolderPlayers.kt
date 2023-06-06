package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentTeamPlayerRowBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked

class ViewHolderPlayers(
    private val binding: TournamentTeamPlayerRowBinding,
    private val listener: OnPlayerClicked
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Player) {
        binding.player.setPlayerInfo(item)
        if (item.id != -1) {
            binding.root.setOnClickListener {
                listener.onPlayerClicked(item)
            }
        } else {
            binding.root.setOnClickListener(null)
        }
    }
}
