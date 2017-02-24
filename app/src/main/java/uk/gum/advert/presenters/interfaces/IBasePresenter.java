package uk.gum.advert.presenters.interfaces;

import io.reactivex.disposables.Disposable;
import rx.Subscription;

/**
 * Created by sniper on 15-Feb-2017.
 */

public interface IBasePresenter {
    void addSubscription(Subscription newSubscription);
    void addDisposable(Disposable newDisposable);
    void destroyAllDisposables();
    void destroyAllSubscriptions();
}
