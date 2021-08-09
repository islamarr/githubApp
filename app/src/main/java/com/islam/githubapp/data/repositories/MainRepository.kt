package com.islam.githubapp.data.repositories

import com.islam.githubapp.data.Resource
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserListResponse

interface MainRepository {

    suspend fun searchUsers(query: String, page: Int, pageSize: Int): Resource<UserListResponse>

}