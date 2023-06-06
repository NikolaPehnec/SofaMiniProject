package com.sofaacademy.sofaminiproject.views.adapters.headerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.SquadHeaderBinding
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.SquadHeaderViewHolder

class SquadHeaderAdapter(private val header: String) :
    RecyclerView.Adapter<SquadHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadHeaderViewHolder {
        return SquadHeaderViewHolder(
            SquadHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SquadHeaderViewHolder, position: Int) {
        holder.bind(header)
    }

    override fun getItemCount(): Int {
        return 1
    }
}
