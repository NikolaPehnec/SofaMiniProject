package com.sofaacademy.sofaminiproject.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.sofaacademy.sofaminiproject.db.SofaDB
import com.sofaacademy.sofaminiproject.db.SofaDao
import com.sofaacademy.sofaminiproject.networking.SofaMiniApi
import com.sofaacademy.sofaminiproject.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideApiService(): SofaMiniApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val gsonBuilder = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(httpClient).build()
            .create(SofaMiniApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): SofaDB {
        return Room.databaseBuilder(
            appContext,
            SofaDB::class.java,
            "SofaDB"
        ).fallbackToDestructiveMigration()
            .setQueryCallback(
                object : RoomDatabase.QueryCallback {
                    override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                        Log.v("SQL", "$sqlQuery SQL Args: $bindArgs")
                    }
                },
                Executors.newSingleThreadExecutor()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideSofaDAO(sofaDB: SofaDB): SofaDao =
        sofaDB.sofaDao()
}
