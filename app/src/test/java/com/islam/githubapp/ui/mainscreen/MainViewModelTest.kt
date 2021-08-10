package com.islam.githubapp.ui.mainscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.islam.githubapp.data.repositories.FakeMainRepositoryTest
import com.islam.githubapp.generalUtils.Const
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    val userFactory = UserFactory()

    private val mockUsers = listOf(
        userFactory.createUser(),
        userFactory.createUser(),
        userFactory.createUser()
    )

    @Test
    fun itemKeyedSubredditPagingSource() = runBlockingTest {

        val pagingSource = MainDataSource("islam", FakeMainRepositoryTest())

       /* assertEquals(
            expected = Pager(
                config = PagingConfig(
                    pageSize = 3,
                    enablePlaceholders = false,
                    initialLoadSize = 3
                ),
                pagingSourceFactory = {
                    pagingSource
                }
            ),
            actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )

        )*/


    }
}