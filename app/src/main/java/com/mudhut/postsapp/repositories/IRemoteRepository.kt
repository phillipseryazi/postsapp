package com.mudhut.postsapp.repositories

import com.mudhut.postsapp.network.models.Comment
import com.mudhut.postsapp.network.models.Post
import com.mudhut.postsapp.network.models.User

interface IRemoteRepository {
    suspend fun getUsers(): List<User>
    suspend fun getPosts(): List<Post>
    suspend fun getComments(): List<Comment>
}
