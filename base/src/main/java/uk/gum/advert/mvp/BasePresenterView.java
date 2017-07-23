package uk.gum.advert.mvp;

public interface BasePresenterView {
    void onRepositoryErrorOccurred(Throwable error);
    void showLoading();
    void hideLoading();
}
