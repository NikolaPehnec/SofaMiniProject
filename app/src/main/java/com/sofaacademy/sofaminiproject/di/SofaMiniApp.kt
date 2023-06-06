package com.sofaacademy.sofaminiproject.di

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SofaMiniApp : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // MultiDex.install(this)
    }
}
