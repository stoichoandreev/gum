package uk.gum.advert.ui.activities.main_activity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.gum.advert.R;
import uk.gum.advert.models.AdvertDetailsViewData;
import uk.gum.advert.ui.activities.testers.MainActivityT;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by sniper on 15-Feb-2017.
 * Here we are testing only the MainActivity layout and how it handle with data binding
 * This test is in isolation of the MainActivity logic, but can test how data binding is working
 * inside the layout
 * NOTE: We are making some part of the test to wait,
 *      because the DataBinding seems to have problems if we try to verify immediately
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityLayoutDataBindingTest {

    @Rule
    public ActivityTestRule<MainActivityT> mActivityRule = new ActivityTestRule<>(MainActivityT.class);

    @Test
    public void shouldCallAndSMSButtonsBeDisabled() throws Exception {
        //we are setting empty details data
        AdvertDetailsViewData simulatedData = new AdvertDetailsViewData.Builder()
                .build();
        mActivityRule.getActivity().onAdvertDetailsReady(simulatedData);
        //test call and sms action buttons when data does not have phone number
        onView(withId(R.id.call_btn))
                .check(matches(withText(R.string.call)))
                .check(matches(isDisplayed()))
                .check(matches(not(isEnabled())));
        onView(withId(R.id.sms_btn))
                .check(matches(withText(R.string.sms)))
                .check(matches(isDisplayed()))
                .check(matches(not(isEnabled())));
    }
    @Test
    public void shouldCallAndSMSButtonsBeEnabled() throws Exception {
        //we are setting just phone number
        AdvertDetailsViewData simulatedData = new AdvertDetailsViewData.Builder()
                .contactNumber("09899933")
                .build();
        mActivityRule.getActivity().onAdvertDetailsReady(simulatedData);
        //make test to wait because the DataBinding seems to have problems if we try to verify immediately
        Thread.sleep(1000);
        //test call and sms action buttons when data does not have phone number
        onView(withId(R.id.call_btn))
                .check(matches(withText(R.string.call)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));
        onView(withId(R.id.sms_btn))
                .check(matches(withText(R.string.sms)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));
    }
    @Test
    public void shouldDisplayAllDetailsDataAndUpdateViewWhenDataChanged() throws Exception {
        AdvertDetailsViewData simulatedData = new AdvertDetailsViewData.Builder()
                .price("10 000")
                .address("45 Chivalry Road, London")
                .date("23-Mar-2016")
                .fuelType("Gassssss")
                .build();
        mActivityRule.getActivity().onAdvertDetailsReady(simulatedData);
        Thread.sleep(1000);
        //test do all details (price, address, date and so on) are presented correctly
        onView(withId(R.id.price_tv))
                .check(matches(withText("10 000")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.address_tv))
                .check(matches(withText("45 Chivalry Road, London")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.date_tv))
                .check(matches(withText("23-Mar-2016")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.fuel_type_tv))
                .check(matches(withText("Gassssss")))
                .check(matches(isDisplayed()));
    }
    @Test
    public void shouldUpdateDetailsDataWhenSomethingChange() throws Exception {
        AdvertDetailsViewData simulatedData = new AdvertDetailsViewData.Builder()
                .price("10 000")
                .build();
        mActivityRule.getActivity().onAdvertDetailsReady(simulatedData);
        Thread.sleep(1000);
        //test does some detail will be updated if data receive update
        onView(withId(R.id.price_tv))
                .check(matches(withText("10 000")))
                .check(matches(isDisplayed()));

        simulatedData.setPrice("11 000");
        Thread.sleep(1000);
        onView(withId(R.id.price_tv))
                .check(matches(withText("11 000")))
                .check(matches(isDisplayed()));
    }
}