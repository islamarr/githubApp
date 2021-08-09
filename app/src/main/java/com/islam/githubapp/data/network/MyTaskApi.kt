package com.islam.githubapp.data.network

import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MyTaskApi {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/users")
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): Response<UserListResponse>

}

