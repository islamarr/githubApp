package com.islam.githubapp.ui.mainscreen

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.islam.githubapp.data.Resource
import com.islam.githubapp.data.repositories.MainRepository
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserResponse

class MainDataSource(private val repository: MainRepository) :
    PagingSource<Int, UserResponse>() {

    companion object {
        private const val START_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        val pos = params.key ?: START_INDEX

        return try {

            val arr = (repository.searchUsers() as Resource.Success).data.list

            LoadResult.Page(
                arr,
                if (pos <= START_INDEX) null else pos - 1,
                if (arr.isEmpty()) null else pos + 1
            )
        } catch (exception: Exception) {
            Log.e("TAG", exception.message.toString())
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int {
        return 0
    }


}