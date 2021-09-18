package com.example.kooapp.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kooapp.models.Post
import com.example.kooapp.paging.PostPagingSource
import kotlinx.coroutines.flow.Flow

class PostRepository {

    fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource() }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}