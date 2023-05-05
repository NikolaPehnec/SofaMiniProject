package com.sofaacademy.sofaminiproject.networking


import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SofaResponse

abstract class BasicRepository {

    protected suspend fun <T : Any> apiCall(call: suspend () -> SofaResponse<T>): Result<T> {
        val response: SofaResponse<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            return Result.Error(Exception(t.message))
        }

        response.body?.let {
            return Result.Success(response.body!!)
        }
        response.status?.let {
            return Result.Error(Exception("STATUS $it"))
        }
        return Result.Error(Exception("no data"))
    }
}
