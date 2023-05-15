package com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentStandingsRowBinding
import com.sofaacademy.sofaminiproject.model.StandingsRow
import com.sofaacademy.sofaminiproject.utils.StandingsDiffUtilCallback
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderStandings

class StandingsArrayAdapter(
    private var highlightTeamId: Int?,
    private var items: MutableList<StandingsRow>,
    private val listener: OnTeamClicked
) : RecyclerView.Adapter<ViewHolderStandings>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStandings {
        return ViewHolderStandings(
            parent.context,
            TournamentStandingsRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolderStandings, position: Int) {
        holder.bind(highlightTeamId, items[position], position + 1)
    }

    fun setItems(newItems: List<StandingsRow>) {
        val diffResult = DiffUtil.calculateDiff(StandingsDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size
}
