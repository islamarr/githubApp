package com.islam.githubapp.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.islam.githubapp.data.repositories.MainRepository
import com.islam.githubapp.generalUtils.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    fun searchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = Const.PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = Const.PAGE_SIZE
            ),
            pagingSourceFactory = {
                MainDataSource(query, repository)
            }
        ).flow

}