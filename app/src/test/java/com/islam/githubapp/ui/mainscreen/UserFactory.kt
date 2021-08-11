package com.islam.githubapp.ui.mainscreen

import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserResponse
import java.util.concurrent.atomic.AtomicInteger

class UserFactory {
    private val counter = AtomicInteger(0)
    fun createUser(): UserResponse {
        val id = counter.incrementAndGet()
        return UserResponse(
            id = id,
            username = "name_$id",
            avatarUrl = "url_$id"
        )
    }
}