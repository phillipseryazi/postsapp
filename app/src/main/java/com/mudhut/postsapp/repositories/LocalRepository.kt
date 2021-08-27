package com.mudhut.postsapp.repositories

import com.mudhut.postsapp.db.daos.MainDao
import com.mudhut.postsapp.db.models.CommentEntity
import com.mudhut.postsapp.db.models.PostEntity
import com.mudhut.postsapp.db.models.UserEntity
import com.mudhut.postsapp.db.relations.PostWithComments
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor(private val mainDao: MainDao) : ILocalRepository {
    override suspend fun insertUser(user: UserEntity) {
        mainDao.insertUser(user)
    }

    override suspend fun insertPost(post: PostEntity) {
        mainDao.insertPost(post)
    }

    override suspend fun insertComment(comment: CommentEntity) {
        mainDao.insertComment(comment)
    }

    override fun getPosts(): Flow<List<PostEntity>> {
        return mainDao.getPosts()
    }

    override suspend fun getPostWithComments(postId: Int): PostWithComments {
        return mainDao.getPostWithComments(postId)
    }

    override suspend fun getUser(userId: Int): UserEntity {
        return mainDao.getUser(userId)
    }
}
