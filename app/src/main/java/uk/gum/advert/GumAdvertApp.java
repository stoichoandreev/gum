package uk.gum.advert;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.VisibleForTesting;

import uk.gum.advert.dagger.components.ApplicationComponent;
import uk.gum.advert.dagger.components.DaggerApplicationComponent;
import uk.gum.advert.dagger.modules.ApplicationModule;

/**
 * Created by sniper on 14-Feb-2017.
 */

public class GumAdvertApp extends Application {
    private static GumAdvertApp instance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyDialog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * Use this method to get instance of GumAdvertApp from Activity, View or something else which provide context
     * @param context - the local context
     * @return - will return the application context
     */
    public static GumAdvertApp get(Context context) {
        return (GumAdvertApp) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
    /**
     * Visible only for testing purposes.
     * This method will be used only for testing purposes to provide a mock implementation
     * of the AppComponent class to test the app flow.
     */
    @VisibleForTesting
    public void setTestComponent(ApplicationComponent testingComponent) {
        applicationComponent = testingComponent;
    }
}
