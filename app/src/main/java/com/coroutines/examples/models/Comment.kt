package com.coroutines.examples.models


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("body")
    var body: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("postId")
    var postId: Int?
)