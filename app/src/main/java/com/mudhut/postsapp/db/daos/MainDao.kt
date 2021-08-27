package com.mudhut.postsapp.db.daos

import androidx.room.*
import com.mudhut.postsapp.db.models.CommentEntity
import com.mudhut.postsapp.db.models.PostEntity
import com.mudhut.postsapp.db.models.UserEntity
import com.mudhut.postsapp.db.relations.PostWithComments
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComment(comment: CommentEntity)

    @Query("SELECT * FROM Posts")
    fun getPosts(): Flow<List<PostEntity>>

    @Transaction
    @Query("SELECT * FROM Posts WHERE id=:postId")
    suspend fun getPostWithComments(postId: Int): PostWithComments

    @Query("SELECT * FROM Users WHERE id=:userId")
    suspend fun getUser(userId: Int): UserEntity
}