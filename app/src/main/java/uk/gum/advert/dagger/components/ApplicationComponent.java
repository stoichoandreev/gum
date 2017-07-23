package uk.gum.advert.dagger.components;


import android.content.Context;

import dagger.Component;
import uk.gum.advert.GumAdvertApp;
import uk.gum.advert.Settings;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.dagger.modules.ApplicationModule;
import uk.gum.advert.dagger.modules.NetworkModule;
import uk.gum.advert.dagger.scopes.ApplicationScope;

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent extends BaseComponent {

    Context context();

    Settings settings();

    ApiService apiService();

    void inject(GumAdvertApp application);
}
