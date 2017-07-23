package uk.gum.advert.ui.addetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import uk.gum.advert.R;
import uk.gum.advert.constants.AdActionType;
import uk.gum.advert.dagger.ComponentsManager;
import uk.gum.advert.dagger.components.AdvertDetailsActivityComponent;
import uk.gum.advert.dagger.components.ApplicationComponent;
import uk.gum.advert.dagger.components.DaggerAdvertDetailsActivityComponent;
import uk.gum.advert.dagger.modules.AdvertDetailsActivityModule;
import uk.gum.advert.databinding.ActivityAdDetailsBinding;
import uk.gum.advert.models.AdDetails;
import uk.gum.advert.presenters.AdvertDetailsPresenter;
import uk.gum.advert.ui.BaseActivity;


public class AdDetailsActivity extends BaseActivity<AdvertDetailsPresenter, AdvertDetailsActivityComponent> implements AdvertDetailsPresenter.View {

    @Inject
    public AdvertDetailsPresenter presenter;

    protected ActivityAdDetailsBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getScreenComponent().inject(this);
        setPresenter(presenter);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_ad_details);

        final int adId = 10;
        presenter.getAdvertDetails(adId);

        presenter.subscribeShareView(RxView.clicks(activityBinding.shareBtn));
        presenter.subscribeCallView(RxView.clicks(activityBinding.callBtn));
        presenter.subscribeSMSView(RxView.clicks(activityBinding.smsBtn));
        presenter.subscribeMessageView(RxView.clicks(activityBinding.messageBtn));

        activityBinding.toolbarInclude.toolbarBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void displayAdvertDetails(@NonNull AdDetails adDetails) {
        activityBinding.setAdDetails(adDetails);
        Picasso.with(activityBinding.currentImage.getContext())
                .load(adDetails.getImages().get(0))
                .into(activityBinding.currentImage);
    }

    @Override
    public void handleShareIntent(@NonNull String type, @NonNull String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(type);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Share"));
    }

    @Override
    public void handleCallIntent(@Nullable String phoneNumber) {
        if (phoneNumber != null) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(AdActionType.TYPE_CALL, phoneNumber, null));
            startActivity(Intent.createChooser(callIntent, "Make a call"));
        } else {
            onRepositoryErrorOccurred(new Throwable("Sorry we can\'t make a call"));
        }
    }

    @Override
    public void handleSMSIntent(@Nullable String phoneNumber, @NonNull String message) {
        if (phoneNumber != null) {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.setData(Uri.parse(AdActionType.TYPE_SMS + phoneNumber));
            smsIntent.putExtra("sms_body", message);

            startActivity(Intent.createChooser(smsIntent, "Send SMS"));
        } else {
            onRepositoryErrorOccurred(new Throwable("Sorry we can\'t send SMS"));
        }
    }

    @Override
    public void handleMessageIntent(@NonNull String message) {
        Toast.makeText(this, "Send platform message", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRepositoryErrorOccurred(@NonNull Throwable error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        //unused
    }

    @Override
    public void hideLoading() {
        //unused
    }

    @Override
    protected AdvertDetailsActivityComponent getScreenComponent() {
        AdvertDetailsActivityComponent component = ComponentsManager.get().getBaseComponent(AdvertDetailsActivityComponent.KEY);
        //If component already exist (in case of screen config change),
        //we would like to use the same presenter instance (we don't want to init everything from scratch, only the view)
        if (component == null) {
            ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
            component = DaggerAdvertDetailsActivityComponent
                    .builder()
                    .applicationComponent(appComponent)
                    .advertDetailsActivityModule(new AdvertDetailsActivityModule(this)).build();
            ComponentsManager.get().putBaseComponent(getComponentKey(), component);
        }
        return component;
    }

    @Override
    protected String getComponentKey() {
        return AdvertDetailsActivityComponent.KEY;
    }
}
