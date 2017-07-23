package uk.gum.advert.dagger.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.dagger.scopes.DetailsActivityScope;
import uk.gum.advert.presenters.DefaultAdvertDetailsPresenter;
import uk.gum.advert.presenters.AdvertDetailsPresenter;


@Module
public class AdvertDetailsActivityModule {

    public final AdvertDetailsPresenter.View view;

    public AdvertDetailsActivityModule(@NonNull AdvertDetailsPresenter.View view) {
        this.view = view;
    }

    @Provides
    @DetailsActivityScope
    AdvertDetailsPresenter provideMainPresenter(@NonNull ApiService apiService) {
        return new DefaultAdvertDetailsPresenter(view, apiService);
    }
}
