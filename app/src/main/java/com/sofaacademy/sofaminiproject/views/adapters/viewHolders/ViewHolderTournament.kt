package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked

class ViewHolderTournament(
    private val binding: TournamentRowBinding,
    private val listener: OnTournamentClicked
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Tournament) {
        binding.tournamentItem.setTournamentInfo(item)
        binding.tournamentItem.setOnClickListener {
            listener.onTournamentClicked(item)
        }
    }
}
