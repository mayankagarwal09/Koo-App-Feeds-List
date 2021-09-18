package com.example.kooapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kooapp.api.PostApi
import com.example.kooapp.models.Post
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource : PagingSource<String, Post>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {

        return try {
            val pageUrl = params.key
            val response =
                if (pageUrl != null) PostApi.retrofitService.getPostsFromLink(pageUrl) else PostApi.retrofitService.getPosts()
            val posts = response.data
            LoadResult.Page(
                data = posts,
                prevKey = response.meta.pagination.links.previous,
                nextKey = response.meta.pagination.links.next
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.id?.toString() }

    }
}