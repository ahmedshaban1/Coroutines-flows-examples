package com.coroutines.examples.models


import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("body")
    var body: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("userId")
    var userId: Int?,
    var comments:MutableList<Comment> = mutableListOf()
)


data class PostEntity(
    var body: String,
    var title: String
)