package com.islam.githubapp.data.repositories

import android.util.Log
import com.islam.githubapp.data.Resource
import com.islam.githubapp.data.network.MyTaskApi
import com.islam.githubapp.generalUtils.ApiException
import com.islam.githubapp.generalUtils.Const
import com.islam.githubapp.generalUtils.NoInternetException
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserListResponse
import javax.inject.Inject


class DefaultMainRepository @Inject constructor(private val api: MyTaskApi) : MainRepository {

    override suspend fun searchUsers(query: String, page: Int, pageSize: Int): Resource<UserListResponse> {
        return try {
            val response = api.searchUsers(Const.token, query, page, pageSize)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Something went wrong, try again!")
            } else {
                Resource.Error("Something went wrong, try again!")
            }
        } catch (e: ApiException) {
            Log.e(TAG, e.toString())
            Resource.Error("Something went wrong, try again!")
        } catch (ne: NoInternetException) {
            Log.e(TAG, ne.toString())
            Resource.Error("Make sure you have an active Internet connection!")
        }
    }

    companion object {
        private const val TAG = "DefaultMainRepo"
    }

}