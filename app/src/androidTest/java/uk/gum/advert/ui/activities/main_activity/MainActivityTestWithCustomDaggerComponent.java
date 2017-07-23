package uk.gum.advert.ui.activities.main_activity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import uk.gum.advert.GumAdvertApp;
import uk.gum.advert.R;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.models.AdDetails;
import uk.gum.advert.dagger.components.ApplicationComponent;
import uk.gum.advert.dagger.modules.ApplicationModule;
import uk.gum.advert.ui.addetails.AdDetailsActivity;
import uk.gum.advert.ui.test_utils.DaggerActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test is testing the MainActivity with some test app component
 * (In this case some TestNetworkModel)
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTestWithCustomDaggerComponent {

    //Before we test MainActivity let's override our Application Module with a Test implementation of the same module (TestApplicationComponent)
    @Rule
    public ActivityTestRule<AdDetailsActivity> mActivityRule =
            new DaggerActivityTestRule<>(AdDetailsActivity.class, (application, activity) -> {
                GumAdvertApp app = (GumAdvertApp) application;

                ApplicationComponent mTestAppComponent = DaggerMainActivityTestWithCustomDaggerComponent_TestApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(app))
                        .build();

                app.setTestComponent(mTestAppComponent);
            });

    @Singleton
    @Component(modules = {TestNetworkModel.class, ApplicationModule.class})
    interface TestApplicationComponent extends ApplicationComponent {}

    //If we have more sub Modules inside our application module we can override them with test values
    @Module
    static class TestNetworkModel {

        @Provides
        @Singleton
        ApiService provideTestApiService() {
            return advertId -> {
                final AdDetails advertTestData = new AdDetails();
                //create Object with some test data
                advertTestData.id = advertId;
                advertTestData.title = "Test title";
                advertTestData.price = "5 000 £";
                advertTestData.address = "SW11 6PZ";
                advertTestData.date = "22-Feb-2017";
                advertTestData.fuelType = "Test Fuel";
                advertTestData.contactNumber = "00000000";
                advertTestData.contactName = "Test Name";
                advertTestData.description = "Test Description";
                advertTestData.images = Collections.singletonList("http://test_url/image.jpg");
                return Single.just(advertTestData);
            };
        }
    }
    @Test
    public void shouldDisplayAllDetailsDataFromTestNetworkModel() throws Exception {
        //test do all details (price, address, date and so on) are presented correctly from the TestNetworkModel
        onView(withId(R.id.price_tv))
                .check(matches(withText("5 000 £")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.address_tv))
                .check(matches(withText("SW11 6PZ")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.date_tv))
                .check(matches(withText("22-Feb-2017")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.fuel_type_tv))
                .check(matches(withText("Test Fuel")))
                .check(matches(isDisplayed()));

        //Test also does data update will update the UI
        mActivityRule.getActivity().presenter.getDetailsData().setDate("29-Feb-2017");
        Thread.sleep(100);
        onView(withId(R.id.date_tv))
                .check(matches(withText("29-Feb-2017")))
                .check(matches(isDisplayed()));
    }
}