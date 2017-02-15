package uk.gum.advert.ui.views;

import android.content.Intent;

import uk.gum.advert.models.AdvertDetailsViewData;

/**
 * Created by sniper on 02/14/17.
 */

public interface AdvertDetailsView extends BaseView{
    void onAdvertDetailsReady(AdvertDetailsViewData advertDetails);
    void onShareIntentReady(Intent shareIntent);
    void onCallIntentReady(Intent callIntent);
    void onSMSIntentReady(Intent smsIntent);
    void onMessageIntentReady(Intent messageIntent);
}
