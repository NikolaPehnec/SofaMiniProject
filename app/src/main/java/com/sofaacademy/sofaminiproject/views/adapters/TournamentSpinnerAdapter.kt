package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.sofaacademy.sofaminiproject.databinding.TournamentSpinnerItemBinding
import com.sofaacademy.sofaminiproject.databinding.TournamentSpinnerRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadTournamentImg

class TournamentSpinnerAdapter(var tournaments: List<Tournament>, val context: Context) :
    BaseAdapter() {

    private val layoutInflater by lazy { LayoutInflater.from(context) }

    override fun getCount(): Int {
        return tournaments.size
    }

    override fun getItem(position: Int): Any {
        return tournaments[position]
    }

    override fun getItemId(position: Int): Long {
        return tournaments[position].id.toLong()
    }

    fun setItems(newTournaments: List<Tournament>) {
        tournaments = newTournaments
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = (
            convertView?.tag ?: TournamentSpinnerRowBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            ) as TournamentSpinnerRowBinding

        val tournament = tournaments[position]
        binding.tournamentName.text = tournament.name
        binding.tournamentImg.loadTournamentImg(tournament.id.toString())
        if (binding.root.tag == null) {
            binding.root.tag = binding
        }
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = (
            convertView?.tag ?: TournamentSpinnerItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            ) as TournamentSpinnerItemBinding

        val tournament = tournaments[position]
        binding.tournamentName.text = tournament.name
        binding.tournamentImg.loadTournamentImg(tournament.id.toString())
        if (binding.root.tag == null) {
            binding.root.tag = binding
        }
        return binding.root
    }
}
