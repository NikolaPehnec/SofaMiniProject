package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.PlayerTeamSearchViewBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadPlayerImg
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadTeamImg
import com.sofaacademy.sofaminiproject.utils.helpers.FlagHelper
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnSearchDelete
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked

class ViewHolderSearch(
    private val binding: PlayerTeamSearchViewBinding,
    private val listenerPlayer: OnPlayerClicked,
    private val listenerTeam: OnTeamClicked,
    private val listenerDelete: OnSearchDelete,
    private val recentSearches: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any, context: Context) {
        when (item) {
            is Player -> {
                binding.name.text = item.name
                binding.img.loadPlayerImg(item.id.toString())
                binding.smallImg.load(FlagHelper.getFlagBitmap(context, item.country!!.name))
                binding.smallName.text = item.country.name
                val res = getSlugDrawable(item.sport!!.slug, context)
                binding.smallImgSport.setImageDrawable(res)
                binding.sportName.text = item.sport.name
                binding.type.text = context.getString(R.string.player)
                binding.root.setOnClickListener {
                    listenerPlayer.onPlayerClicked(item)
                }
                binding.deleteItem.setOnClickListener {
                    listenerDelete.onPlayerDeleteClicked(item)
                }
            }

            is Team2 -> {
                binding.name.text = item.name
                binding.img.loadTeamImg(item.id.toString())
                binding.smallImg.load(FlagHelper.getFlagBitmap(context, item.country.name))
                binding.smallName.text = item.country.name
                binding.smallImgSport.setImageDrawable(getSlugDrawable(item.sport!!.slug, context))
                binding.sportName.text = item.sport.name
                binding.type.text = context.getString(R.string.team)
                binding.root.setOnClickListener {
                    listenerTeam.onTeamClicked(item)
                }
                binding.deleteItem.setOnClickListener {
                    listenerDelete.onTeamDeleteClicked(item)
                }
            }
        }

        binding.deleteItem.visibility = if (recentSearches) View.VISIBLE else View.GONE
    }

    private fun getSlugDrawable(slug: String, context: Context): Drawable? {
        return when (slug) {
            Constants.SLUG_FOOTBALL -> AppCompatResources.getDrawable(
                context,
                R.drawable.icon_football
            )

            Constants.SLUG_BASKETBALL -> AppCompatResources.getDrawable(
                context,
                R.drawable.icon_basketball
            )

            Constants.SLUG_AMERICAN_FOOTBALL -> AppCompatResources.getDrawable(
                context,
                R.drawable.icon_american_football
            )

            else -> AppCompatResources.getDrawable(context, R.drawable.icon_football)
        }
    }
}
