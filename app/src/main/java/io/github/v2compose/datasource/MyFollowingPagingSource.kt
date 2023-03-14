package io.github.v2compose.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.v2compose.network.NetConstants
import io.github.v2compose.network.V2exService
import io.github.v2compose.network.bean.MyFollowingInfo
import javax.inject.Inject

class MyFollowingPagingSource @Inject constructor(private val v2exService: V2exService) :
    PagingSource<Int, MyFollowingInfo.Item>() {
    override fun getRefreshKey(state: PagingState<Int, MyFollowingInfo.Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyFollowingInfo.Item> {
        return try {
            val page = params.key ?: 1
            val result = v2exService.myFollowingInfo(page, NetConstants.systemUserAgent)
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (page < result.totalPageCount) page + 1 else null
            LoadResult.Page(result.items, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}