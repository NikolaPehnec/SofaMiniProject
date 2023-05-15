package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.SquadHeaderBinding

class SquadHeaderViewHolder(private val binding: SquadHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(header: String) {
        binding.headerTitle.text = header
    }
}
