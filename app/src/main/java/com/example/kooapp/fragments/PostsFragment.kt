package com.example.kooapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.kooapp.adapters.PostAdapter
import com.example.kooapp.adapters.PostLoadStateAdapter
import com.example.kooapp.databinding.FragmentPostsBinding
import com.example.kooapp.repositories.PostRepository
import com.example.kooapp.viewModels.PostViewModel
import com.example.kooapp.viewModels.PostViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostsFragment : Fragment() {

    lateinit var binding: FragmentPostsBinding

    private val postViewModel: PostViewModel by activityViewModels {
        PostViewModelFactory(PostRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(layoutInflater, container, false)

        val postAdapter = PostAdapter()
        binding.postList.adapter = postAdapter.withLoadStateHeaderAndFooter(
            header = PostLoadStateAdapter { postAdapter.retry() },
            footer = PostLoadStateAdapter { postAdapter.retry() }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            postViewModel.getPosts().collectLatest {
                postAdapter.submitData(it)
            }
        }

        return binding.root
    }

}