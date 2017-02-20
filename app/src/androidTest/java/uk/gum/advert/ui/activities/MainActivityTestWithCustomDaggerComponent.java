package uk.gum.advert.ui.activities;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import uk.gum.advert.GumAdvertApp;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;
import uk.gum.advert.dagger.components.ApplicationComponent;
import uk.gum.advert.dagger.modules.ApplicationModule;
import uk.gum.advert.ui.test_utils.DaggerActivityTestRule;

/**
 * Created by sniper on 15-Feb-2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTestWithCustomDaggerComponent {

    //Before we test MainActivity let's override our Application Module with a Test implementation of the same module (TestApplicationComponent)
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new DaggerActivityTestRule<>(MainActivity.class, (application, activity) -> {
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
                final AdvertDetailsResponseParseData advertTestData = new AdvertDetailsResponseParseData();
                //create Object with some test data
                advertTestData.id = advertId;
                advertTestData.title = "Test title";
                advertTestData.price = "5 000 £";
                advertTestData.address = "Test Address";
                advertTestData.date = "Test Date";
                advertTestData.fuelType = "Test Fuel";
                advertTestData.contactNumber = "00000000";
                advertTestData.contactName = "Test Name";
                advertTestData.description = "Test Description";
                advertTestData.images = Arrays.asList("http://test_url/image.jpg");
                //return cold observable for subscribers can receive some data
                return Observable.just(advertTestData);
            };
        }
    }
}