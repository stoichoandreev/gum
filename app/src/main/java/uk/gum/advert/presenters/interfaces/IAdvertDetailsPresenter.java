package uk.gum.advert.presenters.interfaces;

import android.content.Intent;

import uk.gum.advert.presenters.AdvertDetailsPresenter;

/**
 * Created by sniper on 02/14/17.
 */

public interface IAdvertDetailsPresenter extends IBasePresenter {
    /**
     * View should use this method to request advert details data for it self
     * @param advertId - the id of the requested advert
     */
    void getAdvertDetailsById(long advertId);

    /**
     * Use this method to create share intent options to the user.
     * @param action - the action of the intent usually Intent.ACTION_SEND (but we don't have default params in Java :))
     * @param type - the type of the shared information usually "text/plain" for text share
     * @return - Share intent if possible or null if share information (text) is missing
     */
    Intent resolveShareAction(String action, String type);
    /**
     * Use this method to create Call or SMS intent because both are based on some phone number
     * @param action - the action of the intent (ACTION_CALL, ACTION_SMS, ACTION_MMS)
     * @return
     */
    Intent resolvePhoneNumberAction(String action, @AdvertDetailsPresenter.PhoneActionType String phoneAction);
    Intent resolveMessageAction();
}
