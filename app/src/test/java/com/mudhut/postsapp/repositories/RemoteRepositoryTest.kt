package com.mudhut.postsapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mudhut.postsapp.enqueueResponse
import com.mudhut.postsapp.network.APIService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class RemoteRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var repository: RemoteRepository
    private lateinit var retrofitInstance: APIService
    private lateinit var client: OkHttpClient

    @Before
    fun setUp() {
        mockWebServer.start(8080)

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        retrofitInstance = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

        repository = RemoteRepository(retrofitInstance)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getUsers`() = runBlocking {
        mockWebServer.enqueueResponse("get_users_response.json", 200)

        val response = repository.getUsers()
        assertThat(response[0].name).isEqualTo("Leanne Graham")
        assertThat(response[1].name).isEqualTo("Ervin Howell")
    }

    @Test
    fun `test getPosts`() = runBlocking {
        mockWebServer.enqueueResponse("get_posts_response.json", 200)

        val response = repository.getPosts()
        assertThat(response[0].title).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
        assertThat(response[1].title).isEqualTo("qui est esse")
    }

    @Test
    fun `test getComments`() = runBlocking {
        mockWebServer.enqueueResponse("get_comments_response.json", 200)

        val response = repository.getComments()
        assertThat(response[0].email).isEqualTo("Eliseo@gardner.biz")
        assertThat(response[1].email).isEqualTo("Jayne_Kuhic@sydney.com")
    }
}
