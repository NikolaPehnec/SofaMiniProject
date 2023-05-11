package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sofaacademy.sofaminiproject.databinding.TournamentDetailRowBinding
import com.sofaacademy.sofaminiproject.databinding.TournamentTeamDetailRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.BASE_TOURNAMENT_URL
import com.sofaacademy.sofaminiproject.utils.Constants.IMG_ENDPOINT
import com.sofaacademy.sofaminiproject.utils.EventDiffUtilCallback

class TeamTournamentsArrayAdapter(
    private val context: Context,
    private var items: MutableList<Tournament>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TeamTournamentsArrayAdapter.ViewHolderTournament>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTournament {
        return ViewHolderTournament(
            TournamentTeamDetailRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderTournament, position: Int) {
        holder.bind(items[position])
    }

    fun getNumberOfItems(): Int = items.size

    fun setItems(newItems: List<Tournament>) {
        val diffResult = DiffUtil.calculateDiff(EventDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(item: Any)
    }

    inner class ViewHolderTournament(private val binding: TournamentTeamDetailRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tournament) {
            binding.tournamentName.text = item.name
            binding.tournamentLogo.load("$BASE_TOURNAMENT_URL${item.id}$IMG_ENDPOINT")
        }
    }
}
