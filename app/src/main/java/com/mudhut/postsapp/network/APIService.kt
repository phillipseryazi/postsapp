package com.mudhut.postsapp.network

import com.mudhut.postsapp.network.models.Comment
import com.mudhut.postsapp.network.models.Post
import com.mudhut.postsapp.network.models.User
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("comments")
    suspend fun getComments(): Response<List<Comment>>
}
