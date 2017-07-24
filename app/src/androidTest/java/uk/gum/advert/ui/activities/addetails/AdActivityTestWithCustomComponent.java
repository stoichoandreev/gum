package uk.gum.advert.ui.activities.addetails;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import uk.gum.advert.GumAdvertApp;
import uk.gum.advert.R;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.dagger.components.ApplicationComponent;
import uk.gum.advert.dagger.modules.ApplicationModule;
import uk.gum.advert.dagger.scopes.ApplicationScope;
import uk.gum.advert.models.AdDetails;
import uk.gum.advert.ui.addetails.AdDetailsActivity;
import uk.gum.advert.ui.test_utils.DaggerActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test MainActivity with a test application component with TestApiServiceModule
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdActivityTestWithCustomComponent {

    //Before we test MainActivity let's override our Application Module with a Test implementation of the Application module (TestApplicationComponent)
    @Rule
    public ActivityTestRule<AdDetailsActivity> mActivityRule =
            new DaggerActivityTestRule<>(AdDetailsActivity.class, (application, activity) -> {
                GumAdvertApp app = (GumAdvertApp) application;

                ApplicationComponent mTestAppComponent = DaggerAdActivityTestWithCustomComponent_TestApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(app))
                        .build();

                app.setTestComponent(mTestAppComponent);
            });

    @ApplicationScope
    @Component(modules = {TestApiServiceModule.class, ApplicationModule.class})
    interface TestApplicationComponent extends ApplicationComponent {}

    //If we have more sub Modules inside our application module we can override them with test values
    @Module
    static class TestApiServiceModule {

        @Provides
        @ApplicationScope
        ApiService provideTestApiService() {
            return adId -> {
                final AdDetails advertTestData = new AdDetails();

                advertTestData.setId(adId);
                advertTestData.setTitle("Test title");
                advertTestData.setPrice("5 000 £");
                advertTestData.setAddress("SW11 6PZ");
                advertTestData.setDate("22-Feb-2017");
                advertTestData.setFuelType("Test Fuel");
                advertTestData.setContactNumber("00000000");
                advertTestData.setContactName("Test Name");
                advertTestData.setDescription("Test Description");
                advertTestData.setImages(Collections.singletonList("http://test_url/image.jpg"));

                return Single.just(advertTestData);
            };
        }
    }
    @Test
    public void shouldDisplayAllDetailsDataFromTestNetworkModel() throws Exception {
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
    }
}