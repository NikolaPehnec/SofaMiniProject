package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentDetailRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadTournamentImg
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked

class ViewHolderTournamentDetail(
    private val binding: TournamentDetailRowBinding,
    private val listener: OnTournamentClicked
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Tournament) {
        binding.tournamentName.text = item.name
        binding.tournamentLogo.loadTournamentImg(item.id.toString())
        binding.root.setOnClickListener {
            listener.onTournamentClicked(item)
        }
    }
}
