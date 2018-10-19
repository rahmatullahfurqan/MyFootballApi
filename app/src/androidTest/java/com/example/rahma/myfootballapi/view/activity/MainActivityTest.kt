package com.example.rahma.myfootballapi.view.activity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.rahma.myfootballapi.R.id.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        //wait 2 sec
        Thread.sleep(2000)
        //check recyclerMatch on MatchFragment
        onView(withId(recyclerMatch)).check(matches(isDisplayed()))
        //scroll recyler to data 10
        onView(withId(recyclerMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        //click data recycler when posisition 10
        onView(withId(recyclerMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
        Thread.sleep(2000)
        //check favorite
        onView(withId(add_fav)).check(matches(isDisplayed()))
        //click favorite, if added favorite then remove from favorite
        //if not favorite then add to favorite
        onView(withId(add_fav)).perform(click())
        Thread.sleep(2000)
        //click back
        pressBack()
        //refresh match layout
        onView(withId(refreshLayout)).perform(ViewActions.swipeDown())
        //check tabsMatch
        val tabMatch = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(tabsMatch), 0), 1), isDisplayed()))
        //click tabmatch
        tabMatch.perform(click())
        //check recyclerNextMatch on MatchNextFragment
        onView(withId(recyclerNextMatch)).check(matches(isDisplayed()))
        //scroll recyler to data 5
        onView(withId(recyclerNextMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        //click data recycler when posisition 5
        onView(withId(recyclerNextMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
        Thread.sleep(2000)
        //check favorite
        onView(withId(add_fav)).check(matches(isDisplayed()))
        //click favorite, if added favorite then remove from favorite
        //if not favorite then add to favorite
        onView(withId(add_fav)).perform(click())
        Thread.sleep(2000)
        //click back
        pressBack()
        //click nav bottom teams
        onView(withId(teams)).perform(click())
        onView(withId(recyclerTeams)).check(matches(isDisplayed()))
        //scroll recyler to data 0
        onView(withId(recyclerTeams)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(2000)
        //click favorite, if added favorite then remove from favorite
        //if not favorite then add to favorite
        onView(withId(add_fav)).check(matches(isDisplayed()))
        onView(withId(add_fav)).perform(click())
        Thread.sleep(2000)
        //click back
        //check tabsMatch
        val tabTeams = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(tabsTeam), 0), 1), isDisplayed()))
        //click tabmatch
        tabTeams.perform(click())
        //check recyclerplayer on playerFragment
        onView(withId(recyclerPlayer)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        //click data recycler when posisition 2
        onView(withId(recyclerPlayer)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, ViewActions.click()))
        Thread.sleep(1000)
        pressBack()
        pressBack()
        //refresh next layout
        onView(withId(refreshTeam)).perform(ViewActions.swipeDown())
        //click nav bottom favoriteMatch
        onView(withId(favorite)).perform(click())
        //check recyclerFavoriteMatch on FavoriteFragment
        onView(withId(recyclerMatchFavorite)).check(matches(isDisplayed()))
        //click data recycler when posisition 0 , array from 0
        onView(withId(recyclerMatchFavorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(2000)
        //click favorite, if added favorite then remove from favorite
        //if not favorite then add to favorite
        onView(withId(add_fav)).check(matches(isDisplayed()))
        onView(withId(add_fav)).perform(click())
        Thread.sleep(2000)
        //click back
        pressBack()
        //refresh match favorite
        onView(withId(refreshFavoriteMatchLayout)).perform(ViewActions.swipeDown())
        val tabFavorite = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(tabsFavorite), 0), 1), isDisplayed()))
        tabFavorite.perform(click())
        Thread.sleep(2000)
        //check recyclerMatch on MatchNextFragment
        onView(withId(recyclerFavoriteTeam)).check(matches(isDisplayed()))
        //scroll recyler to data 10
        onView(withId(recyclerFavoriteTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(2000)
        //check favorite
        onView(withId(add_fav)).check(matches(isDisplayed()))
        //click favorite, if added favorite then remove from favorite
        //if not favorite then add to favorite
        onView(withId(add_fav)).perform(click())
        Thread.sleep(2000)
        //click back
        pressBack()
        //refresh Teaams favorite
        onView(withId(refreshFavoriteTeamsLayout)).perform(ViewActions.swipeDown())
        Thread.sleep(2000)


    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}