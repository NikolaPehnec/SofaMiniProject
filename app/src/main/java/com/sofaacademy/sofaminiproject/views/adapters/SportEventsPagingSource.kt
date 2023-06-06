package com.sofaacademy.sofaminiproject.views.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.sortedByDateDesc

class SportEventsPagingSource(
    private val fetchNextEvents: suspend (page: String) -> List<SportEvent>,
    private val fetchLastEvents: suspend (page: String) -> List<SportEvent>
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

        val teamEvents =
            if (page <= 0) {
                page *= -1
                fetchNextEvents.invoke(page.toString())
            } else {
                page -= 1
                fetchLastEvents.invoke(page.toString())
            }.sortedByDateDesc()

        return LoadResult.Page(
            data = teamEvents,
            prevKey = if (teamEvents.isNotEmpty()) initialKey - 1 else null,
            nextKey = if (teamEvents.isNotEmpty()) initialKey + 1 else null
        )
    }
}
