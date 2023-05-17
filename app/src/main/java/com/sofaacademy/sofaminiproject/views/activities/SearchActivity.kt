package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivitySearchBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants.SEARCH_TRESHOLD
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.viewmodel.SearchViewModel
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.SearchArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), OnTeamClicked, OnPlayerClicked {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchArrayAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        searchAdapter = SearchArrayAdapter(mutableListOf(), this, this, this)
        binding.searchRv.adapter = searchAdapter
        setListeners()

        binding.autoCompleteTv.requestFocus()
    }

    private fun setListeners() {
        searchViewModel.searchTeamPlayers.observe(this) {
            searchAdapter.setItems(it)
        }

        binding.autoCompleteTv.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    this@SearchActivity,
                    R.drawable.ic_baseline_search_24
                ),
                null,
                null,
                null
            )

            addTextChangedListener {
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        this@SearchActivity,
                        R.drawable.ic_baseline_search_24
                    ),
                    null,
                    if (it.toString().isEmpty()) {
                        null
                    } else {
                        ContextCompat.getDrawable(
                            this@SearchActivity,
                            R.drawable.ic_baseline_close_24
                        )
                    },
                    null
                )

                if (it.toString().length >= SEARCH_TRESHOLD) {
                    searchViewModel.searchTeamsPlayers(it.toString())
                }
            }

            // Search ili brisanje teksta ovisno o kliku na koji drawable
            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    val drawableLeftIndex = 0
                    val drawableRightIndex = 2

                    if (event?.action == MotionEvent.ACTION_UP) {
                        binding.autoCompleteTv.compoundDrawables[drawableRightIndex]?.let {
                            if (event.rawX >= (binding.autoCompleteTv.right - it.bounds.width() + 100)) {
                                binding.autoCompleteTv.setText("")
                                return true
                            } else if (event.rawX <= (binding.autoCompleteTv.left + binding.autoCompleteTv.compoundDrawables[drawableLeftIndex].bounds.width())) {
                                // startSearch(binding.autoCompleteTv.text.toString().lowercase())
                                return true
                            }
                        }
                    }
                    return false
                }
            })

            setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding.autoCompleteTv.text.toString().apply {
                        // startSearch(this)
                    }
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPlayerClicked(player: Player) {
        PlayerDetailsActivity.start(player, null, this)
    }

    override fun onTeamClicked(team: Team2) {
        TeamDetailsActivity.start(team, this)
    }
}
