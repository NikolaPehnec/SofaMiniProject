package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.TeamPlayerViewBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadCountryFlag
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadPlayerImg

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
        binding.countryLogo.loadCountryFlag(player.country.name, context)
        // Za coacha
        if (player.id == -1) {
            binding.playerImg.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.team_manager
                )
            )
        } else {
            binding.playerImg.loadPlayerImg(player.id.toString())
        }
    }
}
