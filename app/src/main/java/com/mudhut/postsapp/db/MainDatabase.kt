package com.mudhut.postsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mudhut.postsapp.db.daos.MainDao
import com.mudhut.postsapp.db.models.CommentEntity
import com.mudhut.postsapp.db.models.PostEntity
import com.mudhut.postsapp.db.models.UserEntity


@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
        CommentEntity::class,
    ],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getMainDao(): MainDao

}
