package uk.gum.advert.dagger.components;

/**
 * Created by sniper on 14-Feb-2017.
 */

import javax.inject.Singleton;

import dagger.Component;
import uk.gum.advert.GumAdvertApp;
import uk.gum.advert.dagger.modules.AdvertDetailsActivityModule;
import uk.gum.advert.dagger.modules.ApplicationModule;
import uk.gum.advert.dagger.modules.NetworkModule;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    GumAdvertApp application();
    AdvertDetailsActivityComponent plus(AdvertDetailsActivityModule module);
    //Here we can add many plus(..... module) methods just to define diff Activities modules (by the same way)
}
