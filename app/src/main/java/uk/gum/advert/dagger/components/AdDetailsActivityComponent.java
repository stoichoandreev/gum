package uk.gum.advert.dagger.components;

import dagger.Component;
import uk.gum.advert.dagger.modules.AdDetailsActivityModule;
import uk.gum.advert.dagger.scopes.AdDetailsActivityScope;
import uk.gum.advert.ui.addetails.AdDetailsActivity;


@AdDetailsActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {AdDetailsActivityModule.class} )
public interface AdDetailsActivityComponent extends BaseComponent {

    String KEY = AdDetailsActivityComponent.class.getSimpleName();

    void inject(AdDetailsActivity activity);

}
