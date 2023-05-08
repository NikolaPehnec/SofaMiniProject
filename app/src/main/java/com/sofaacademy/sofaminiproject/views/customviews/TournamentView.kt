package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.TournamentViewBinding
import com.sofaacademy.sofaminiproject.utils.Constants.BASE_TOURNAMENT_URL
import com.sofaacademy.sofaminiproject.utils.Constants.IMG_ENDPOINT

class TournamentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: TournamentViewBinding = TournamentViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.Tournament, 0, 0).apply {
            try {
                binding.country.text = getString(R.styleable.Tournament_countryName)
                binding.league.text = getString(R.styleable.Tournament_tournamentName)
            } finally {
                recycle()
            }
        }
    }

    fun loadTournamentLogo(tournamentId: Int) {
        binding.tournamentLogo.load("$BASE_TOURNAMENT_URL$tournamentId$IMG_ENDPOINT")
    }

    fun setTournamentInfo(leagueName: String, countryName: String) {
        binding.league.text = leagueName
        binding.country.text = countryName
    }
}
