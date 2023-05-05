package com.sofaacademy.sofaminiproject.networking

import android.content.Context
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SportEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

open class SofaMiniRepository @Inject constructor(
    val context: Context,
    private val sofaMiniApi: SofaMiniApi
) : BasicRepository() {

    suspend fun getSportEvents(slug: String, date: String): Result<List<SportEvent>> =
        suspendCoroutine {
            val callback = object : Callback<List<SportEvent>> {
                override fun onResponse(
                    call: Call<List<SportEvent>>,
                    response: Response<List<SportEvent>>
                ) {
                    it.resume(Result.Success(response.body()!!))
                }

                override fun onFailure(call: Call<List<SportEvent>>, t: Throwable) {
                    it.resume(Result.Error(Exception(t)))
                }
            }
            sofaMiniApi.getSportEvent(slug,date).enqueue(callback)
        }

}
