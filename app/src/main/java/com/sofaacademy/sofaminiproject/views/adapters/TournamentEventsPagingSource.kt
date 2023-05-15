package com.sofaacademy.sofaminiproject.views.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.sortedByDateDesc

class TournamentEventsPagingSource(
    private val sofaMiniRepository: SofaMiniRepository,
    val tournamentId: String
) : PagingSource<Int, Any>() {
    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        var page = params.key ?: 0
        val initialKey = params.key ?: 0

        val result =
            if (page <= 0) {
                page *= -1
                sofaMiniRepository.getTournamentEvents(
                    tournamentId,
                    Constants.NEXT,
                    page.toString()
                )
            } else {
                page -= 1
                sofaMiniRepository.getTournamentEvents(
                    tournamentId,
                    Constants.LAST,
                    page.toString()
                )
            }

        return if (result is Result.Success) {
            val res = result.data.groupBy { it.round }.flatMap {
                listOf(it.key) + it.value.sortedByDateDesc()
            }
            LoadResult.Page(
                data = res,
                prevKey = if (res.isNotEmpty()) initialKey - 1 else null,
                nextKey = if (res.isNotEmpty()) initialKey + 1 else null
            )
        } else {
            LoadResult.Error(Throwable("Err"))
        }
    }
}
