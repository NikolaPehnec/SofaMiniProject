package com.sofaacademy.sofaminiproject.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentStandingsHeaderBinding
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderStandingsHeader

class StandingsHeaderArrayAdapter() : RecyclerView.Adapter<ViewHolderStandingsHeader>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStandingsHeader {
        return ViewHolderStandingsHeader(
            TournamentStandingsHeaderBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderStandingsHeader, position: Int) {
    }

    override fun getItemCount(): Int = 1
}
