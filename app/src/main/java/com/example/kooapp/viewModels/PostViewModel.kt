package com.example.kooapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kooapp.models.Post
import com.example.kooapp.repositories.PostRepository
import kotlinx.coroutines.flow.Flow

class PostViewModel(val postRepository: PostRepository) : ViewModel() {


    fun getPosts(): Flow<PagingData<Post>> {
        return postRepository.getPosts()
            .cachedIn(viewModelScope)
    }
}

@Suppress("UNCHECKED_CAST")
class PostViewModelFactory(val postRepository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(postRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}