package com.mudhut.postsapp.repositories

import android.util.Log
import com.mudhut.postsapp.network.APIService
import com.mudhut.postsapp.network.models.Comment
import com.mudhut.postsapp.network.models.Post
import com.mudhut.postsapp.network.models.User
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: APIService) : IRemoteRepository {
    override suspend fun getUsers(): List<User> {
        var items: List<User> = emptyList()
        try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                items = response.body()!!
                Log.d("users", response.body().toString())
            }
        } catch (e: Exception) {
            Log.e("getUsers", e.message.toString())
        }
        return items
    }

    override suspend fun getPosts(): List<Post> {
        var items: List<Post> = emptyList()
        try {
            val response = apiService.getPosts()
            if (response.isSuccessful) {
                items = response.body()!!
            }
        } catch (e: Exception) {
            Log.e("getPosts", e.message.toString())
        }
        return items
    }

    override suspend fun getComments(): List<Comment> {
        var items: List<Comment> = emptyList()
        try {
            val response = apiService.getComments()
            if (response.isSuccessful) {
                items = response.body()!!
            }
        } catch (e: Exception) {
            Log.e("getComments", e.message.toString())
        }
        return items
    }

}
