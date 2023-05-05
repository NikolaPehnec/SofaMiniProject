package com.sofaacademy.sofaminiproject.networking

import android.content.Context
import javax.inject.Inject

open class SofaMiniRepository @Inject constructor(
    val context: Context,
    private val sofaMiniApi: SofaMiniApi
) : BasicRepository() {

}
