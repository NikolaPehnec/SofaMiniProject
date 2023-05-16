package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.os.bundleOf
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivitySettingsBinding
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.saveThemePreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.languages)
        )

        binding.languagesDropdown.apply {
            setStringArrayAdapter(adapter)
            setOnItemClickListener { _, _, _, _ ->
                changeLocalization(getSelectedItemText())
            }
            setText(getCurrentLanguage())
        }

        when (UtilityFunctions.getThemePreferences(this)) {
            Constants.NIGHT_THEME -> binding.themeDark.isChecked = true
            Constants.LIGHT_THEME -> binding.themeLight.isChecked = true
        }

        binding.themeRg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.themeLight.id -> {
                    if (getDefaultNightMode() != MODE_NIGHT_NO) {
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                        saveThemePreference(Constants.LIGHT_THEME, this)
                    }
                }

                binding.themeDark.id -> {
                    if (getDefaultNightMode() != MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                        saveThemePreference(Constants.NIGHT_THEME, this)
                    }
                }
            }
        }
    }

    override fun onResume() {
        binding.languagesDropdown.setText(getCurrentLanguage())
        binding.languagesDropdown.apply {
            setStringArrayAdapter(
                ArrayAdapter(
                    this@SettingsActivity,
                    android.R.layout.simple_list_item_1,
                    resources.getStringArray(R.array.languages)
                )
            )
        }
        super.onResume()
    }

    private fun getCurrentLanguage(): String {
        return when (resources.configuration.locales.get(0).toString()) {
            Constants.LANG_EN -> resources.getStringArray(R.array.languages)[0]
            Constants.LANG_HR -> resources.getStringArray(R.array.languages)[1]
            else -> ""
        }
    }

    private fun changeLocalization(language: String) {
        val appLocale = when (language) {
            // English
            resources.getStringArray(R.array.languages)[0] -> {
                LocaleListCompat.forLanguageTags(Constants.LANG_EN)
            }
            // Croatian
            resources.getStringArray(R.array.languages)[1] -> {
                LocaleListCompat.forLanguageTags(Constants.LANG_HR)
            }

            else -> null
        }

        appLocale?.let {
            AppCompatDelegate.setApplicationLocales(appLocale)
        }

        binding.languagesDropdown.setText(language)
    }

    override fun onBackPressed() {
        finish()
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

}
