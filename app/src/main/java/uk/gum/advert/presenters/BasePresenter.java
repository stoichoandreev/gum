package uk.gum.advert.presenters;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V, R> {

    protected V mView;
    protected R mRepository;
    protected CompositeSubscription mCompositeSubscription;

    protected BasePresenter() {
        mCompositeSubscription = new CompositeSubscription();
    }

    public BasePresenter(V view, R repository) {
        this();
        this.mView = view;
        this.mRepository = repository;
    }

    /**
     * Add subscription to the CompositeSubscription set
     * @param newSubscription - the subscription to be added
     */
    public void addSubscription(Subscription newSubscription){
        if(newSubscription != null){
            mCompositeSubscription.add(newSubscription);
        }
    }

    /**
     * Remove (clear) all subscriptions from our CompositeSubscription set.
     * This will prevent some memory leak
     */
    public void destroyAllSubscriptions(){
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}