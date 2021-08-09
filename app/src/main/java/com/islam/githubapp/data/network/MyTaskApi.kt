package com.islam.githubapp.data.network

import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyTaskApi {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") q: String
    ): Response<UserListResponse>

}

