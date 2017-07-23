package uk.gum.advert;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import timber.log.Timber;
import uk.gum.advert.dagger.ComponentsManager;
import uk.gum.advert.dagger.components.ApplicationComponent;
import uk.gum.advert.dagger.components.DaggerApplicationComponent;
import uk.gum.advert.dagger.modules.ApplicationModule;
import uk.gum.advert.utils.ReleaseTree;

public class GumAdvertApp extends Application {

    @Inject
    Settings settings;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger(this);
        if (settings.isDebug()) {
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

        initTimber();
    }

    private void initDagger(@NonNull final Application application) {
        final ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
        ComponentsManager.get().setAppComponent(applicationComponent);
        applicationComponent.inject(this);
    }

    private void initTimber() {
        Timber.plant(settings.isDebug()
                ? new Timber.DebugTree() {
                    @Override
                    protected String createStackElementTag(StackTraceElement element) {
                        return super.createStackElementTag(element) + ": " + element.getLineNumber();
                    }
                }
                : new ReleaseTree());
    }

    /**
     * Visible only for testing purposes.
     * This method will be used only for testing purposes to provide a mock implementation
     * of the AppComponent class to test the app flow.
     * @param testingComponent - the test component which have test implementation of our ApplicationComponent
     */
    @VisibleForTesting
    public void setTestComponent(ApplicationComponent testingComponent) {
        ComponentsManager.get().setAppComponent(testingComponent);
    }
}
