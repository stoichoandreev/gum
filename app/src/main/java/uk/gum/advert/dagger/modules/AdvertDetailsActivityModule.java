package uk.gum.advert.dagger.modules;

import dagger.Module;
import dagger.Provides;
import uk.gum.advert.dagger.scopes.ActivityScope;
import uk.gum.advert.presenters.AdvertDetailsPresenter;
import uk.gum.advert.presenters.interfaces.IAdvertDetailsPresenter;
import uk.gum.advert.repos.AdvertDetailsRepository;
import uk.gum.advert.repos.interfaces.IAdvertDetailsRepository;
import uk.gum.advert.ui.views.AdvertDetailsView;

/**
 * Created by sniper on 14-Feb-2017.
 */

@Module
public class AdvertDetailsActivityModule {
    public final AdvertDetailsView view;

    public AdvertDetailsActivityModule(AdvertDetailsView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    AdvertDetailsView provideAdvertDetailsView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    IAdvertDetailsRepository provideAdvertDetailsRepository(AdvertDetailsRepository repository) {
        return repository;
    }

    @Provides
    @ActivityScope
    IAdvertDetailsPresenter provideMainPresenter(AdvertDetailsPresenter presenter) {
        return presenter;
    }
}
