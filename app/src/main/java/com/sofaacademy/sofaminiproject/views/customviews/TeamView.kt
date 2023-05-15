package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.sofaacademy.sofaminiproject.databinding.TeamViewBinding
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadTeamImg

class TeamView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: TeamViewBinding = TeamViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setTeamName(
        teamName: String
    ) {
        binding.teamName.text = teamName
    }

    fun loadTeamLogo(teamHomeId: Int) {
        binding.teamLogo.loadTeamImg(teamHomeId.toString())
    }
}
