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

    val userFactory = UserFactory()

    private val fakeUsers = listOf(
        userFactory.createUser(),
        userFactory.createUser(),
        userFactory.createUser()
    )

    @Test
    fun `load Returns Page When OnSuccessfulLoad Of Page Keyed Data`() = runBlockingTest {

        val pagingSource = MainDataSource("user", FakeMainRepositoryTest())

        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = fakeUsers,
                prevKey = null,
                nextKey = fakeUsers[1].id
            ),
            actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

        )


    }
}