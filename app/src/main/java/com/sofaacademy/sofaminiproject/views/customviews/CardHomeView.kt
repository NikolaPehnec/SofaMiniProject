package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.CardHomeRowBinding
import com.sofaacademy.sofaminiproject.model.CardColor
import com.sofaacademy.sofaminiproject.model.Incident

class CardHomeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CardHomeRowBinding = CardHomeRowBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setCardIncidentInfo(
        cardIncident: Incident.CardIncident
    ) {
        binding.playerName.text = cardIncident.player?.name
        binding.minute.text = cardIncident.time?.toString() + "'"
        when (cardIncident.color!!) {
            CardColor.RED.color -> binding.icCard.load(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_card_red
                )
            )

            // Nema crvenoZutog resursa na zeplinu?
            CardColor.YELLOW.color -> binding.icCard.load(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_card_yellow
                )
            )

            CardColor.YELLOWRED.color -> binding.icCard.load(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_card_red
                )
            )
        }
        // Hardkodirano foul
        // binding.argument.text=cardIncident.?
    }
}
