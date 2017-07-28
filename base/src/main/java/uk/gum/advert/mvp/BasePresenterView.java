package uk.gum.advert.mvp;

public interface BasePresenterView {
    void showError(Throwable error);
    void showLoading();
    void hideLoading();
}
