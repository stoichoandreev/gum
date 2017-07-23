package uk.gum.advert.ui.activities.main_activity;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.gum.advert.R;
import uk.gum.advert.ui.addetails.AdDetailsActivity;
import uk.gum.advert.ui.test_utils.MyTestUtils;
import uk.gum.advert.ui.views.AdvertDetailsView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by sniper on 15-Feb-2017.
 * Launch this test on Android API > 21 because it will have problems with DataBinding
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdDetailsActivityTest {

    @Rule
    public ActivityTestRule<AdDetailsActivity> mActivityRule = new ActivityTestRule<>(AdDetailsActivity.class);

    @Test
    public void testDoesMainActivityImplementTheRightInterface() throws Exception {
        MyTestUtils.implementsInterface(mActivityRule.getActivity(), AdvertDetailsView.class);
    }

    @Test
    public void testBaseViewsInitialState() throws Exception {
        //test toolbar default title
        onView(withId(R.id.toolbar_title)).check(matches(withText("")));
        //at the beginning toolbar back arrow should be visible
        onView(withId(R.id.toolbar_back)).check(matches(isDisplayed()));
        //test all action buttons
        onView(withId(R.id.call_btn))
                .check(matches(withText(R.string.call)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.sms_btn))
                .check(matches(withText(R.string.sms)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.message_btn))
                .check(matches(withText(R.string.message)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCallIntent() throws Exception {
        onView(withId(R.id.call_btn)).perform(click());
        intended(allOf(hasAction(Intent.ACTION_CHOOSER),
                hasData("some Uri ")));
    }
}