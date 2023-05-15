package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import coil.load
import com.sofaacademy.sofaminiproject.databinding.TeamPlayerViewBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.Constants.IMG_ENDPOINT
import com.sofaacademy.sofaminiproject.utils.helpers.FlagHelper

class PlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: TeamPlayerViewBinding = TeamPlayerViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setPlayerInfo(
        player: Player
    ) {
        binding.playerName.text = player.name
        binding.countryName.text = player.country!!.name
        binding.playerImg.load("${Constants.BASE_PLAYER_URL}${player.id}$IMG_ENDPOINT")
        binding.countryLogo.load(
            FlagHelper.getFlagBitmap(
                context,
                player.country.name
            )
        )
    }
}
