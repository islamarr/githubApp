package com.islam.githubapp.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.islam.githubapp.data.network.GitHubService
import com.islam.githubapp.data.network.internet.ConnectivityInterCeptor
import com.islam.githubapp.data.network.internet.ConnectivityInterCeptorImpl
import com.islam.githubapp.data.repositories.DefaultMainRepository
import com.islam.githubapp.data.repositories.MainRepository
import com.islam.githubapp.generalUtils.Utils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMainRepository(api: GitHubService) =
        DefaultMainRepository(api) as MainRepository

    @Singleton
    @Provides
    fun provideConnectivityInterCeptor(@ApplicationContext context: Context): ConnectivityInterCeptor {
        return ConnectivityInterCeptorImpl(context)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideAPI(
        connectivityInterCeptor: ConnectivityInterCeptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): GitHubService {

        val okkHttpclient = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(connectivityInterCeptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okkHttpclient)
            .baseUrl(Utils.getUrl())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GitHubService::class.java)

    }

}

















