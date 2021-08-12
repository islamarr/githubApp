package com.islam.githubapp.ui.mainscreen

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.islam.githubapp.R
import com.islam.githubapp.generalUtils.Const
import com.islam.githubapp.launchFragmentInHiltContainer
import com.islam.githubapp.ui.adapters.MainAdapter
import com.islam.task.utils.EspressoIdlingResourceRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    val espressoIdlingResoureRule = EspressoIdlingResourceRule()

    lateinit var scenario: Unit

    @Before
    fun setup() {
        // scenario = launchFragmentInHiltContainer<MainFragment>(Bundle(), R.style.Theme_MyTask)
    }

    @Test
    fun launchFragmentReturnsStarterImageAtFirstLaunch() {
        onView(withId(R.id.starterImage)).check(matches(isDisplayed()))
    }

    @Test
    fun loadsTheDefaultResults() {
        onView(withId(R.id.list)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(0, recyclerView.adapter?.itemCount)
        }
    }

    @Test
    fun checkToolbarSearchIcon() {
        onView(withId(R.id.search)).check(matches(isDisplayed()))
    }

    @Test
    fun searchForNameReturnsList() {

        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("name_1"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        onView(withId(R.id.list)).check(
            matches(
                atPosition(
                    0,
                    hasDescendant(withText("name_1"))
                )
            )
        )

    }

    @Test
    fun clickOneItem_navigateToUserDetails() {

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<MainFragment> {
            Navigation.setViewNavController(this.view!!, navController)
        }
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("name_1"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        closeSoftKeyboard()
        onView(withId(R.id.list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<MainAdapter.ViewHolder>(0, click()))

        val bundle = Bundle()
        bundle.putString(Const.UserDetailsKey, "name_1")
        verify(navController).navigate(
            MainFragmentDirections.actionMainFragmentToUserDetailsFragment(
                "name_1"
            )
        )

    }

    private fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }
        }
    }

}