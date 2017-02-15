package uk.gum.advert.dagger.components;

import dagger.Subcomponent;
import uk.gum.advert.dagger.modules.AdvertDetailsActivityModule;
import uk.gum.advert.dagger.scopes.ActivityScope;
import uk.gum.advert.ui.activities.MainActivity;

/**
 * Created by sniper on 14-Feb-2017.
 */

@ActivityScope
@Subcomponent(modules = {AdvertDetailsActivityModule.class} )
public interface AdvertDetailsActivityComponent {
    void inject(MainActivity activity);
}
