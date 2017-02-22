package uk.gum.advert.ui.activities.testers;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import uk.gum.advert.R;
import uk.gum.advert.databinding.ActivityMainBinding;
import uk.gum.advert.models.AdvertDetailsViewData;
import uk.gum.advert.ui.views.AdvertDetailsView;

public class MainActivityT extends AppCompatActivity implements AdvertDetailsView {

    private ActivityMainBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //Inject the presenter, it will inject all pieces from the MVP pattern about this feature (Advert Details screen)
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
    }

    @Override
    public void onCallIntentReady(Intent callIntent) {
    }

    @Override
    public void onSMSIntentReady(Intent smsIntent) {
    }

    @Override
    public void onMessageIntentReady(Intent messageIntent) {
    }

    @Override
    public void onRepositoryErrorOccurred(Throwable error) {
        Toast.makeText(this, error != null && error.getMessage() != null ? error.getMessage() : "Error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProgressVisibility(int visibility) {
    }
}
