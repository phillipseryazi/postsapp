package com.mudhut.postsapp.di

import android.content.Context
import androidx.room.Room
import com.mudhut.postsapp.db.MainDatabase
import com.mudhut.postsapp.db.daos.MainDao
import com.mudhut.postsapp.network.APIService
import com.mudhut.postsapp.network.NetworkConnectionInterceptor
import com.mudhut.postsapp.repositories.ILocalRepository
import com.mudhut.postsapp.repositories.IRemoteRepository
import com.mudhut.postsapp.repositories.LocalRepository
import com.mudhut.postsapp.repositories.RemoteRepository
import com.mudhut.postsapp.utils.BASE_URL
import com.mudhut.postsapp.utils.MAIN_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMainDatabase(@ApplicationContext applicationContext: Context): MainDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MainDatabase::class.java,
            MAIN_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesMainDao(database: MainDatabase) = database.getMainDao()

    @Singleton
    @Provides
    fun providesNetworkInterceptor(@ApplicationContext context: Context) =
        NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun providesHttpClient(
        networkInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): APIService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        return retrofit.create(APIService::class.java)
    }

    @Singleton
    @Provides
    fun providesLocalRepository(mainDao: MainDao) =
        LocalRepository(mainDao) as ILocalRepository

    @Singleton
    @Provides
    fun providesRemoteRepository(apiService: APIService) =
        RemoteRepository(apiService) as IRemoteRepository
}
