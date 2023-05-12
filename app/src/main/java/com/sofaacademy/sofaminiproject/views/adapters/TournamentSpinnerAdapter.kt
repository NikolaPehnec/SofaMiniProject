package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil.load
import com.sofaacademy.sofaminiproject.databinding.TournamentSpinnerRowBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.BASE_TOURNAMENT_URL
import com.sofaacademy.sofaminiproject.utils.Constants.IMG_ENDPOINT

class TournamentSpinnerAdapter(var tournaments: List<Tournament>, val context: Context) : BaseAdapter() {
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
        val binding = TournamentSpinnerRowBinding.inflate(LayoutInflater.from(context), parent, false)
        val tournament = tournaments[position]
        binding.tournamentName.text = tournament.name
        // Dodat extension za ucitavanje slike entiteta
        binding.tournamentImg.load("${BASE_TOURNAMENT_URL}${tournament.id}$IMG_ENDPOINT")
        return binding.root
    }
}
