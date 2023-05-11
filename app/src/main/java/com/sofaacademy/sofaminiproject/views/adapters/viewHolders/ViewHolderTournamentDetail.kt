package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sofaacademy.sofaminiproject.databinding.TournamentDetailRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked

class ViewHolderTournamentDetail(
    private val binding: TournamentDetailRowBinding,
    private val listener: OnTournamentClicked
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Tournament) {
        binding.tournamentName.text = item.name
        binding.tournamentLogo.load("${Constants.BASE_TOURNAMENT_URL}${item.id}${Constants.IMG_ENDPOINT}")
        binding.root.setOnClickListener {
            listener.onTournamentClicked(item)
        }
    }
}
