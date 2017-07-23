package uk.gum.advert.dagger.modules;

import dagger.Module;
import dagger.Provides;
import uk.gum.advert.Settings;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.api.DefaultApiService;
import uk.gum.advert.dagger.scopes.ApplicationScope;


@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    ApiService provideApiService(Settings settings) {
        return new DefaultApiService(settings);
    }

}
