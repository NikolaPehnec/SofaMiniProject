package com.sofaacademy.sofaminiproject.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

data class TabRowItem(
    val title: String,
    @DrawableRes
    val iconRes: Int,
    val screen: @Composable () -> Unit
)
