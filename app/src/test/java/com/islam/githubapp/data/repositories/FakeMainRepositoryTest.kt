package com.islam.githubapp.data.repositories

import com.islam.githubapp.data.Resource
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserListResponse
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserResponse

class FakeMainRepositoryTest(
    private val fakeUsers: List<UserResponse>,
    private var shouldReturnNetworkError: Boolean
) : MainRepository {

    override suspend fun searchUsers(
        query: String,
        page: Int,
        pageSize: Int
    ): Resource<UserListResponse> {

        return if (shouldReturnNetworkError) {
            Resource.Error("Error")
        } else {
            Resource.Success(
                UserListResponse(
                    fakeUsers.size, fakeUsers
                )
            )
        }

    }
}