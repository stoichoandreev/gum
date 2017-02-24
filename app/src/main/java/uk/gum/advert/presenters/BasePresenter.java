package uk.gum.advert.presenters;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V, R> {

    protected V mView;
    protected R mRepository;
    protected CompositeSubscription mCompositeSubscriptions;
    protected CompositeDisposable mCompositeDisposables;

    protected BasePresenter() {
        mCompositeSubscriptions = new CompositeSubscription();
        mCompositeDisposables = new CompositeDisposable();
    }

    public BasePresenter(V view, R repository) {
        this();
        this.mView = view;
        this.mRepository = repository;
    }

    /**
     * Add disposable(subscription in RX Java 1) to the CompositeDisposable set
     * @param newDisposable - the disposable (old name was subscription) to be added
     */
    public void addDisposable(Disposable newDisposable){
        if(newDisposable != null){
            mCompositeDisposables.add(newDisposable);
        }
    }

    /**
     * Dispose (remove) all disposables from our CompositeDisposable set.
     * This will help to prevent some memory leak
     */
    public void destroyAllDisposables(){
        if(mCompositeDisposables != null) {
            mCompositeDisposables.dispose();
        }
    }
    /**
     * TODO When RxBinding receive RXJava 2 support then we have to remove this method
     */
    public void addSubscription(Subscription newSubscription){
        if(newSubscription != null){
            mCompositeSubscriptions.add(newSubscription);
        }
    }

    /**
     * TODO When RxBinding receive RXJava 2 support then we have to remove this method
     */
    public void destroyAllSubscriptions(){
        if(mCompositeSubscriptions != null) {
            mCompositeSubscriptions.unsubscribe();
        }
    }
}