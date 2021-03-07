package com.coroutines.examples.network

import com.coroutines.examples.models.PostModel
import retrofit2.http.GET

interface ExampleApi {
    @GET("posts")
    suspend fun getPosts():List<PostModel>
}