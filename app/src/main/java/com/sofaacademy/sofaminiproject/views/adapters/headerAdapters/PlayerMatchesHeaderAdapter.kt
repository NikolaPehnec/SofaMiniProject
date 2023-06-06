package com.sofaacademy.sofaminiproject.views.adapters.headerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentMatchHeaderBinding
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.TournamentRoundHeaderViewHolder

class PlayerMatchesHeaderAdapter(private val header: String) :
    RecyclerView.Adapter<TournamentRoundHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentRoundHeaderViewHolder {
        return TournamentRoundHeaderViewHolder(
            TournamentMatchHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TournamentRoundHeaderViewHolder, position: Int) {
        holder.bind(header)
    }

    override fun getItemCount(): Int {
        return 1
    }
}
