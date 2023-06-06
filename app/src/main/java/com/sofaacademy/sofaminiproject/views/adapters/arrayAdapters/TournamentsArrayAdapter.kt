package com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.TournamentDetailRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.diffUtilCallbacks.EventDiffUtilCallback
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderTournamentDetail

class TournamentsArrayAdapter(
    private var items: MutableList<Tournament>,
    private val listener: OnTournamentClicked
) : RecyclerView.Adapter<ViewHolderTournamentDetail>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTournamentDetail {
        return ViewHolderTournamentDetail(
            TournamentDetailRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolderTournamentDetail, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<Tournament>) {
        val diffResult = DiffUtil.calculateDiff(EventDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size
}
