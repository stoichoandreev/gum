package uk.gum.advert.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import uk.gum.advert.GumAdvertApp;
import uk.gum.advert.R;
import uk.gum.advert.dagger.modules.AdvertDetailsActivityModule;
import uk.gum.advert.databinding.ActivityMainBinding;
import uk.gum.advert.models.AdvertDetailsViewData;
import uk.gum.advert.presenters.AdvertDetailsPresenter;
import uk.gum.advert.presenters.interfaces.IAdvertDetailsPresenter;
import uk.gum.advert.ui.views.AdvertDetailsView;

//TODO this should extend BaseActivity class
public class MainActivity extends AppCompatActivity implements AdvertDetailsView {

    @Inject
    IAdvertDetailsPresenter presenter;

    private ActivityMainBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //Inject the presenter, it will inject all pieces from the MVP pattern about this feature (Advert Details screen)
        GumAdvertApp.get(this)
                .getApplicationComponent()
                .plus(new AdvertDetailsActivityModule(this))
                .inject(this);
        presenter.getAdvertDetailsById(10);//we can return use some real id if we need

        presenter.addSubscription(RxView.clicks(activityBinding.shareBtn).subscribe( x -> onShareIntentReady(presenter.resolveShareAction(Intent.ACTION_SEND, "text/plain"))));
        presenter.addSubscription(RxView.clicks(activityBinding.callBtn).subscribe( x -> onCallIntentReady(presenter.resolvePhoneNumberAction(Intent.ACTION_CALL, AdvertDetailsPresenter.TYPE_CALL))));
        presenter.addSubscription(RxView.clicks(activityBinding.smsBtn).subscribe( x -> onSMSIntentReady(presenter.resolvePhoneNumberAction(Intent.ACTION_VIEW, AdvertDetailsPresenter.TYPE_SMS))));
        presenter.addSubscription(RxView.clicks(activityBinding.messageBtn).subscribe( x -> onMessageIntentReady(presenter.resolveMessageAction())));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //TODO When device rotate the callback will come here, because we have android:configChanges="orientation|keyboardHidden|screenSize" for this Activity
        //TODO If we need diff layout for landscape and portrait modes then we need to remove android:configChanges="orientation|keyboardHidden|screenSize" and keep the data Observable somehow live when this Activity has been destroyed,
        //TODO then we need to create two layouts for R.layout.activity_main and the ui will automatically updated to the new device orientation
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyAllSubscriptions();
    }

    @Override
    public void onAdvertDetailsReady(AdvertDetailsViewData advertDetails) {
        if(advertDetails != null) {
            activityBinding.setAdvertDetails(advertDetails);
        } else {
            onRepositoryErrorOccurred(new Throwable("Sorry some error happen"));
        }
    }

    @Override
    public void onShareIntentReady(Intent shareIntent) {
        if(shareIntent != null){
            startActivity(Intent.createChooser(shareIntent, "Share"));
        }else {
            onRepositoryErrorOccurred(new Throwable("Sorry we can\'t share this advert"));
        }
    }

    @Override
    public void onCallIntentReady(Intent callIntent) {
        if(callIntent != null){
            startActivity(Intent.createChooser(callIntent, "Make a call"));
        }else {
            onRepositoryErrorOccurred(new Throwable("Sorry we can\'t make a call"));
        }
    }

    @Override
    public void onSMSIntentReady(Intent smsIntent) {
        if(smsIntent != null){
            startActivity(Intent.createChooser(smsIntent, "Send SMS"));
        }else {
            onRepositoryErrorOccurred(new Throwable("Sorry we can\'t send SMS"));
        }
    }

    @Override
    public void onMessageIntentReady(Intent messageIntent) {
        if(messageIntent != null){
            //TODO Need some custom implementation here, because probably this type of message will be internal
        }else {
            onRepositoryErrorOccurred(new Throwable("Sorry we don\'t know how to send this kind of Message"));
        }
    }

    @Override
    public void onRepositoryErrorOccurred(Throwable error) {
        Toast.makeText(this, error != null && error.getMessage() != null ? error.getMessage() : "Error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProgressVisibility(int visibility) {
        //TODO Need to implement some loading indication
    }
}
