package uk.gum.advert;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import uk.gum.advert.mvp.BasePresenterView;

public abstract class BasePresenter<V extends BasePresenterView> {

    protected V mView;
    private CompositeDisposable compositeDisposable;

    protected BasePresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    public BasePresenter(V view) {
        this();
        this.mView = view;
    }

    /**
     * Add disposable(subscription) to the CompositeDisposable set
     * @param newDisposable - the disposable to be added
     */
    public void addDisposable(Disposable newDisposable) {
        if (newDisposable != null) {
            getCompositeDisposable().add(newDisposable);
        }
    }

    public void clearAllDisposables() {
        if (getCompositeDisposable() != null) {
            getCompositeDisposable().dispose();
        }
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}