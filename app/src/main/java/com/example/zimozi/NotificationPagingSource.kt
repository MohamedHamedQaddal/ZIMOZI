package com.example.zimozi

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.zimozi.model.NotificationEntity
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import kotlin.math.max



class NotificationPagingSource : PagingSource<Int, NotificationEntity>() {

    private val firstNotificationCreatedTime = LocalDateTime.now()

    companion object {
        private const val STARTING_KEY = 0
        private const val LOAD_DELAY_MILLIS = 3_000L
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationEntity> {
        val startKey = params.key ?: STARTING_KEY

        val range = startKey.until(startKey + params.loadSize)

        if (startKey != STARTING_KEY) delay(LOAD_DELAY_MILLIS)
        return LoadResult.Page(
            data = range.map { number ->
                NotificationEntity(
                    notificationHead = "Article $number",
                    notificationBody = "This describes article $number",
                    notificationTime = firstNotificationCreatedTime.minusDays(number.toLong())
                )
            },
            prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = range.last + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, NotificationEntity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val notification = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = notification.id - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}