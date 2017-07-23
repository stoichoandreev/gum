package uk.gum.advert;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import uk.gum.advert.mvp.BasePresenterView;

public abstract class BasePresenter<V extends BasePresenterView> {

    protected V mView;
    private CompositeDisposable mCompositeDisposables;

    protected BasePresenter() {
        mCompositeDisposables = new CompositeDisposable();
    }

    public BasePresenter(V view) {
        this();
        this.mView = view;
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

    public void clearAllDisposables(){
        if(mCompositeDisposables != null) {
            mCompositeDisposables.dispose();
        }
    }
}