package uk.gum.advert.presenters;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;
import uk.gum.advert.models.AdvertDetailsViewData;
import uk.gum.advert.presenters.interfaces.IAdvertDetailsPresenter;
import uk.gum.advert.repos.interfaces.IAdvertDetailsRepository;
import uk.gum.advert.ui.views.AdvertDetailsView;

/**
 * Created by sniper on 02/14/17.
 */

public class AdvertDetailsPresenter extends BasePresenter<AdvertDetailsView, IAdvertDetailsRepository> implements IAdvertDetailsPresenter{

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_CALL, TYPE_SMS, TYPE_MMS})
    public @interface PhoneActionType {}

    public static final String TYPE_CALL = "tel:";
    public static final String TYPE_SMS = "sms:";
    public static final String TYPE_MMS = "mms:";

    private AdvertDetailsViewData detailsData;

    @Inject
    public AdvertDetailsPresenter(AdvertDetailsView mainView, IAdvertDetailsRepository repository) {
        super(mainView, repository);
    }

    @Override
    public void getAdvertDetailsById(long advertId){
        if(advertId <= 0){
            mView.onRepositoryErrorOccurred(new Throwable("Please provide advert ID !"));
            return;
        }
        final Observable<AdvertDetailsResponseParseData> observable = mRepository.requestAdvertData(advertId);
        observable.subscribe(new Observer<AdvertDetailsResponseParseData>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.setProgressVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(AdvertDetailsResponseParseData value) {
                mView.setProgressVisibility(View.INVISIBLE);
                //We can do this conversion with RX flatMap for example, but doing it with AdvertDetailsViewData.fromApiResponse(value)
                // is giving us chance to test the conversion much easier with simple Unit test
                setDetailsData(AdvertDetailsViewData.fromApiResponse(value));
                mView.onAdvertDetailsReady(getDetailsData());
            }

            @Override
            public void onError(Throwable e) {
                mView.setProgressVisibility(View.INVISIBLE);
                mView.onRepositoryErrorOccurred(e);
            }

            @Override
            public void onComplete() {}
        });
    }

    @Override
    public Intent resolveShareAction(String action, String type) {
        if(action == null || type == null) return null;

        Intent shareIntent = new Intent(action);
        shareIntent.setType(type);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getShareText());
        return shareIntent;
    }

    @Nullable
    @Override
    public Intent resolvePhoneNumberAction(String action, @PhoneActionType String phoneAction) {
        if(action == null || phoneAction == null) return null;
        return getPhoneNumber() != null ? new Intent(action, Uri.parse(phoneAction + getPhoneNumber())) : null;
    }

    @Override
    public Intent resolveMessageAction() {
        //TODO need some implementation here, but we don't know how this kind of messages will sent
        return null;
    }

    @Override
    public String getShareText() {
        return getDetailsData() != null ? getDetailsData().getTitle() : "Default share text";
    }

    @Nullable
    @Override
    public String getPhoneNumber() {
        return getDetailsData() != null ? getDetailsData().getContactNumber() : null;
    }

    @Override
    public void setDetailsData(AdvertDetailsViewData data) {
        detailsData = data;
    }

    @Override
    public AdvertDetailsViewData getDetailsData() {
        return detailsData;
    }
}
