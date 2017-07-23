package uk.gum.advert.presenters;

import io.reactivex.Observable;
import uk.gum.advert.models.AdDetails;
import uk.gum.advert.mvp.BasePresenterView;
import uk.gum.advert.mvp.Presenter;

public interface AdvertDetailsPresenter extends Presenter {
    /**
     * View should use this method to request advert details data for it self
     * @param advertId - the id of the requested advert
     */
    void getAdvertDetails(long advertId);

    /**
     * Share view to be subscribed for click events
     * @param viewObservable - Cold observable (attached to a view) ready for subscriber
     */
    void subscribeShareView(Observable<Object> viewObservable);

    /**
     * Call button view to be subscribed for click events
     * @param viewObservable - Cold observable (attached to a view) ready for subscriber
     */
    void subscribeCallView(Observable<Object> viewObservable);

    /**
     * SMS button view to be subscribed for click events
     * @param viewObservable - Cold observable (attached to a view) ready for subscriber
     */
    void subscribeSMSView(Observable<Object> viewObservable);

    /**
     * Platform Message button view to be subscribed for click events
     * @param viewObservable - Cold observable (attached to a view) ready for subscriber
     */
    void subscribeMessageView(Observable<Object> viewObservable);


    interface View extends BasePresenterView {

        /**
         * Send all details data to the View. Data should be ready for presentation
         * @param adDetails - Data object with all ad details
         */
        void displayAdvertDetails(AdDetails adDetails);

        /**
         * Send share text to the view, where share intent will start
         * @param type - type of the share data
         * @param shareText - content of the sharing (some message)
         */
        void handleShareIntent(String type, String shareText);
        void handleCallIntent(String phoneNumber);
        void handleSMSIntent(String phoneNumber, String message);
        void handleMessageIntent(String message);

    }
}
