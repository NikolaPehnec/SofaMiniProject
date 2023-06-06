package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentMatchHeaderBinding

class TournamentRoundHeaderViewHolder(private val binding: TournamentMatchHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(round: String) {
        binding.headerTitle.text = round
    }
}
