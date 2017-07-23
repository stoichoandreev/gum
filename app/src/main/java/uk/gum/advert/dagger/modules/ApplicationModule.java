package uk.gum.advert.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import uk.gum.advert.Settings;
import uk.gum.advert.dagger.scopes.ApplicationScope;

@Module
public class ApplicationModule {

    Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Context provideApplication() {
        return application;
    }

    @Provides
    @ApplicationScope
    Settings provideSettings() {
        return new Settings();
    }

    @Provides
    @ApplicationScope
    Resources provideResources() {
        return application.getResources();
    }
}
