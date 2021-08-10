/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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