package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sofaacademy.sofaminiproject.databinding.TournamentDetailRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.EventDiffUtilCallback

class TournamentsArrayAdapter(
    private val context: Context,
    private var items: MutableList<Tournament>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TournamentsArrayAdapter.ViewHolderTournament>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTournament {
        return ViewHolderTournament(
            TournamentDetailRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
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


    inner class ViewHolderTournament(private val binding: TournamentDetailRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tournament) {
            binding.tournamentName.text = item.name
            binding.tournamentLogo.load(Constants.BASE_URL + "tournament/" + item.id + "/image")
        }
    }


}