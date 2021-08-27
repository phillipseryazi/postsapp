package com.mudhut.postsapp.repositories

import com.mudhut.postsapp.db.models.CommentEntity
import com.mudhut.postsapp.db.models.PostEntity
import com.mudhut.postsapp.db.models.UserEntity
import com.mudhut.postsapp.db.relations.PostWithComments
import kotlinx.coroutines.flow.Flow

interface ILocalRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun insertPost(post: PostEntity)
    suspend fun insertComment(comment: CommentEntity)
    fun getPosts(): Flow<List<PostEntity>>
    suspend fun getPostWithComments(postId: Int): PostWithComments
    suspend fun getUser(userId: Int): UserEntity
}
