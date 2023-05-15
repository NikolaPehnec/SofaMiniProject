package com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentTeamPlayerRowBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.utils.PlayersDiffUtilCallback
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderPlayers

class TeamPlayersArrayAdapter(
    private var items: MutableList<Player>,
    private val listener: OnPlayerClicked
) : RecyclerView.Adapter<ViewHolderPlayers>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPlayers {
        return ViewHolderPlayers(
            TournamentTeamPlayerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolderPlayers, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<Player>) {
        val diffResult = DiffUtil.calculateDiff(PlayersDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size
}
