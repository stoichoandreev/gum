package uk.gum.advert.ui.activities;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.gum.advert.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by sniper on 15-Feb-2017.
 * Launch this test on Android API > 21 because it will have problems with DataBinding
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testToolbarInitialState(){
        //test toolbar default title
        onView(withId(R.id.toolbar_title)).check(matches(withText("")));
        //at the beginning toolbar back arrow should be visible
        onView(withId(R.id.toolbar_back)).check(matches(isDisplayed()));
    }

    @Test
    public void testBaseViewsInitialState() throws Exception {
        //TODO Need to write more instrumentation tests about MainActivity
    }
}