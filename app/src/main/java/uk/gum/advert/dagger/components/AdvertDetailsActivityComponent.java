package uk.gum.advert.dagger.components;

import dagger.Component;
import uk.gum.advert.dagger.modules.AdvertDetailsActivityModule;
import uk.gum.advert.dagger.scopes.DetailsActivityScope;
import uk.gum.advert.ui.addetails.AdDetailsActivity;


@DetailsActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {AdvertDetailsActivityModule.class} )
public interface AdvertDetailsActivityComponent extends BaseComponent {

    String KEY = AdvertDetailsActivityComponent.class.getSimpleName();

    void inject(AdDetailsActivity activity);

}
