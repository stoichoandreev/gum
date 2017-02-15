package uk.gum.advert.dagger.modules;

import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.gum.advert.GumAdvertApp;

/**
 * Created by sniper on 14-Feb-2017.
 */

@Module
public class ApplicationModule {

    GumAdvertApp mApplication;

    public ApplicationModule(GumAdvertApp application){
        this.mApplication = application;
    }
    @Singleton
    @Provides
    GumAdvertApp provideApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return mApplication.getResources();
    }
}
