package com.islam.githubapp.ui.mainscreen

import androidx.paging.PagingSource
import com.islam.githubapp.data.repositories.FakeMainRepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainDataSourceTest {

    private val userFactory = UserFactory()

    private val fakeUsers = listOf(
        userFactory.createUser(),
        userFactory.createUser(),
        userFactory.createUser()
    )

    @Test
    fun `load Returns Page When OnSuccessfulLoad Of Page Keyed Data`() = runBlockingTest {
        val pagingSource = MainDataSource("user", FakeMainRepositoryTest(fakeUsers, false))
        kotlin.test.assertEquals(

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