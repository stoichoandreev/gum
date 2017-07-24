package uk.gum.advert.dagger.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.dagger.scopes.AdDetailsActivityScope;
import uk.gum.advert.presenters.AdDetailsPresenter;
import uk.gum.advert.presenters.DefaultAdDetailsPresenter;


@Module
public class AdDetailsActivityModule {

    public final AdDetailsPresenter.View view;

    public AdDetailsActivityModule(@NonNull AdDetailsPresenter.View view) {
        this.view = view;
    }

    @Provides
    @AdDetailsActivityScope
    AdDetailsPresenter provideMainPresenter(@NonNull ApiService apiService) {
        return new DefaultAdDetailsPresenter(view, apiService);
    }
}
