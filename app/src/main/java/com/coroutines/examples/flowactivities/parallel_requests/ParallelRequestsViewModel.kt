package com.coroutines.examples.flowactivities.parallel_requests

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.examples.models.Comment
import com.coroutines.examples.models.PostModel
import com.coroutines.examples.network.ExampleApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ParallelRequestsViewModel(
    private val api: ExampleApi
) : ViewModel() {

    private val _state = MutableSharedFlow<Resource<List<PostModel>>>()
    val state = _state.asSharedFlow()

    fun loadUserDataAndComments() {
        viewModelScope.launch {
            _state.emit(Resource.Loading)
            val posts = api.getPosts()
            val comments = posts.map {
                async { api.getPostsComments(it.id!!) }
            }
            val data = comments.awaitAll()
            posts.forEach { post ->
                post.comments =
                    data.first { comment -> post.id == comment[0].postId }.toMutableList()
            }
            _state.emit(Resource.Success(posts))

        }
    }

}


sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}