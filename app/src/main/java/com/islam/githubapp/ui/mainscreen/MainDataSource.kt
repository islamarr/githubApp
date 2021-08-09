package com.islam.githubapp.ui.mainscreen

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.islam.githubapp.data.Resource
import com.islam.githubapp.data.repositories.DefaultMainRepository
import com.islam.githubapp.data.repositories.MainRepository
import com.islam.githubapp.generalUtils.ApiException
import com.islam.githubapp.generalUtils.Const
import com.islam.githubapp.generalUtils.NoInternetException
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserResponse
import retrofit2.HttpException
import java.io.IOException

class MainDataSource(private val query: String, private val repository: MainRepository) :
    PagingSource<Int, UserResponse>() {

    companion object {
        private const val START_INDEX = 1
        private const val TAG = "zxcMainDataSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {

        return try {
            val page = params.key ?: START_INDEX
            val response = repository.searchUsers(query, page, Const.PAGE_SIZE)

            when (response) {
                is Resource.Success -> {

                    val userList = response.data.list

                    LoadResult.Page(
                        data = userList,
                        prevKey = if (page <= START_INDEX) null else page - 1,
                        nextKey = if (userList.isEmpty()) null else page + 1
                    )
                }
                else -> {
                    LoadResult.Error(throw Exception())
                }
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int {
        return 0
    }


}