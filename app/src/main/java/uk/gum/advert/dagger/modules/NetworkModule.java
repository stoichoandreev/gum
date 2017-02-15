package uk.gum.advert.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.api.MyApiService;

/**
 * Created by sniper on 14-Feb-2017.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    ApiService provideApiService(){
        return new MyApiService();
    }
}
