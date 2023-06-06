package com.sofaacademy.sofaminiproject.views.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.sortedByDateDesc

/**
 * Paging source with different next and previous logic so it is not generalized
 */
class PlayerEventsPagingSource(
    private val sofaMiniRepository: SofaMiniRepository,
    private val playerId: String
) : PagingSource<Int, Any>() {
    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * Matches that the player played in sorted by start date
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        var page = params.key ?: 0
        val initialKey = params.key ?: 0
        if (page < 0) page *= -1

        val playerEvents =
            sofaMiniRepository.getPlayerEvents(playerId, Constants.LAST, page.toString())
                .sortedByDateDesc()
        return LoadResult.Page(
            data = playerEvents,
            prevKey = null,
            nextKey = if (playerEvents.isNotEmpty()) initialKey - 1 else null
        )
    }
}
