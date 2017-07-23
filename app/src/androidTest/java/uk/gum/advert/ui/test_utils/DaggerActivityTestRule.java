package uk.gum.advert.ui.test_utils;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;


public class DaggerActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

    private final OnBeforeActivityLaunchedListener<T> mListener;

    public interface OnBeforeActivityLaunchedListener<T> {
        void beforeActivityLaunched(@NonNull Application application, @NonNull T activity);
    }

    public DaggerActivityTestRule(Class<T> activityClass,
                                  @NonNull OnBeforeActivityLaunchedListener<T> listener) {
        this(activityClass, false, listener);
    }

    public DaggerActivityTestRule(Class<T> activityClass, boolean initialTouchMode,
                                  @NonNull OnBeforeActivityLaunchedListener<T> listener) {
        this(activityClass, initialTouchMode, true, listener);
    }

    public DaggerActivityTestRule(Class<T> activityClass, boolean initialTouchMode,
                                  boolean launchActivity,
                                  @NonNull OnBeforeActivityLaunchedListener<T> listener) {
        super(activityClass, initialTouchMode, launchActivity);
        mListener = listener;
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        mListener.beforeActivityLaunched(
                (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext(),
                getActivity());
    }
}
