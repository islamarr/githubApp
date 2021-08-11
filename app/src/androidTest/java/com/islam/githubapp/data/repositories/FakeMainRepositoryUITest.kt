package com.islam.githubapp.data.repositories

import com.islam.githubapp.data.Resource
import com.islam.githubapp.ui.mainscreen.UserFactoryUI
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserListResponse

class FakeMainRepositoryUITest : MainRepository {

    private var shouldReturnNetworkError = false

    val userFactory = UserFactoryUI()

    private val fakeUsers = listOf(
        userFactory.createUser(),
        userFactory.createUser(),
        userFactory.createUser()
    )

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