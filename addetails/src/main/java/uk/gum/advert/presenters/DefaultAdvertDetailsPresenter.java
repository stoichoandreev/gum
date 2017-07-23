package uk.gum.advert.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import uk.gum.advert.BasePresenter;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.models.AdvertDetails;


public class DefaultAdvertDetailsPresenter extends BasePresenter<AdvertDetailsPresenter.View> implements AdvertDetailsPresenter {

    private ApiService apiService;
    private AdvertDetails detailsData;

    public DefaultAdvertDetailsPresenter(@NonNull AdvertDetailsPresenter.View view,
                                         @NonNull ApiService apiService) {
        super(view);
        this.apiService = apiService;
    }

    @Override
    public void getAdvertDetails(long advertId){
        if(advertId <= 0){
            mView.onRepositoryErrorOccurred(new Throwable("Please provide Ad ID !"));
            return;
        }
        addDisposable(apiService.getAdvertDetails(advertId).doOnSubscribe( __ -> mView.showLoading())
                .doFinally( () -> mView.hideLoading())
                .subscribe( data -> {
                    setDetailsData(data);
                    mView.displayAdvertDetails(getDetailsData());
                }, error -> mView.onRepositoryErrorOccurred(error))
        );
    }

    @Override
    public void subscribeShareView(@NonNull Observable<Object> viewObservable) {
        viewObservable.subscribe(x -> mView.handleShareIntent("text/plain", getShareText()));
    }

    @Override
    public void subscribeCallView(@NonNull Observable<Object> viewObservable) {
        viewObservable.subscribe(x -> mView.handleCallIntent(getPhoneNumber()));
    }

    @Override
    public void subscribeSMSView(@NonNull Observable<Object> viewObservable) {
        viewObservable.subscribe(x -> mView.handleSMSIntent(getPhoneNumber()));
    }

    @Override
    public void subscribeMessageView(@NonNull Observable<Object> viewObservable) {
        viewObservable.subscribe(x -> mView.handleMessageIntent("Some message to be shared across the platform"));
    }

    @NonNull
    String getShareText() {
        return getDetailsData() != null ? getDetailsData().getTitle() : "Default share text";
    }

    @Nullable
    String getPhoneNumber() {
        return getDetailsData() != null ? getDetailsData().getContactNumber() : null;
    }

    @Override
    public void destroy() {
        super.clearAllDisposables();
    }

    void setDetailsData(AdvertDetails data) {
        detailsData = data;
    }

    AdvertDetails getDetailsData() {
        return detailsData;
    }
}
