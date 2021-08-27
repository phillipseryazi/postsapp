package com.mudhut.postsapp.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mudhut.postsapp.db.models.CommentEntity
import com.mudhut.postsapp.db.models.PostEntity

data class PostWithComments(
    @Embedded val post: PostEntity,
    @Relation(parentColumn = "id", entityColumn = "postId")
    val comments: List<CommentEntity>
)