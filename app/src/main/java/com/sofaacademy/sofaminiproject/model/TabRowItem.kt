package com.sofaacademy.sofaminiproject.model

import androidx.annotation.DrawableRes

data class TabRowItem(
    val title: String,
    @DrawableRes
    val iconRes: Int,
    val slug: String
)
