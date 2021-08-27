package com.mudhut.postsapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.mudhut.postsapp.db.daos.MainDao
import com.mudhut.postsapp.db.models.*
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainDatabaseTest {
    private lateinit var mainDao: MainDao
    private lateinit var db: MainDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, MainDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        mainDao = db.getMainDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertUser() = runBlocking {
        val user = UserEntity(
            1,
            "test",
            "test@gmail.com",
            Address(
                "test-street",
                "test-suite",
                "test-city",
                "+652",
                Geo("12.00", "13.00")
            ),
            "0758123321",
            "www.test.com",
            Company("test-co", "we test", "we are here")
        )
        mainDao.insertUser(user)
        assertNotNull(mainDao.getUser(1))
    }

    @Test
    fun testInsertPost() = runBlocking {
        // create user
        val user = UserEntity(
            1,
            "test",
            "test@gmail.com",
            Address(
                "test-street",
                "test-suite",
                "test-city",
                "+652",
                Geo("12.00", "13.00")
            ),
            "0758123321",
            "www.test.com",
            Company("test-co", "we test", "we are here")
        )
        mainDao.insertUser(user)

        // create post
        val post = PostEntity(1, 1, "test title", "this is a test post")
        mainDao.insertPost(post)
        val item = mainDao.getPosts().first()

        // assert
        assertThat(item[0].title).isEqualTo("test title")
    }
}
