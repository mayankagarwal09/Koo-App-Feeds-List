package com.example.kooapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kooapp.api.PostApi
import com.example.kooapp.models.Post
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val pageIndex = params.key ?: 1
        return try {
            val response = PostApi.retrofitService.getPostsFromPage(pageIndex)
            val posts = response.data
            val nextKey =
                if (posts.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / response.meta.pagination.limit)
                }
            LoadResult.Page(
                data = posts,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}