package uk.gum.advert.ui.test_utils;

import android.support.annotation.NonNull;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class MyTestUtils {
    /**
     * Simple callback interface when Waiting finish
     */
    public interface TestCallBack{
        void onWaitFinish();
    }

    /**
     * Perform action of waiting for a specific view id.
     * E.g.:
     * onView(isRoot()).perform(waitId(R.id.dialogEditor, Sampling.SECONDS_15));
     *
     * @param viewId - the id of the view which we are waiting
     * @param millis - the time (in milliseconds) which
     * @return
     */
    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    /**
     * Use this method to wait for while before you execute something for testing
     * @param millis - the time for waiting in milliseconds
     * @param viewClazz - the view class which will perform the action when wait time finish
     * @param callBack - the call back object which will receive the call when waiting time finish, most probably to do some actions
     * @return
     */
    public static ViewAction waitForWhile(final long millis, @NonNull Class<? extends View> viewClazz, @NonNull final TestCallBack callBack) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(viewClazz);
            }

            @Override
            public String getDescription() {
                return "wait for a while";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
                callBack.onWaitFinish();
            }
        };
    }
    /**
     * Gets the text from a TextView
     * @param matcher The matched view to extract text from
     * @return The string (text) in the view
     */
    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    /**
     * Use this method to test does some object implement some Interface
     * @param object - the instance of the object
     * @param interF - the interface which need to be tested for implementation
     * @return - true of the interface has been implemented
     */
    public static boolean implementsInterface(Object object, Class interF){
        //interF.isInstance(object) will not fail if object is null, that's why we need object check
        return object != null && interF.isInstance(object);
    }
}
