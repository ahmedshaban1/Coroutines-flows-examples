package com.coroutines.examples.network

import com.coroutines.examples.models.Comment
import com.coroutines.examples.models.PostModel
import com.coroutines.examples.models.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ExampleApi {
    @GET("posts")
    suspend fun getPosts():List<PostModel>

    @GET("users")
    suspend fun getUsers():List<UserModel>

    @GET("comments")
    suspend fun getPostsComments(@Query("postId") id: Int): List<Comment>
}