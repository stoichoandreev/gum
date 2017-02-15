package uk.gum.advert.ui.views;

/**
 * Created by sniper on 02/13/17.
 */

public interface BaseView {
    void onRepositoryErrorOccurred(Throwable error);
    void setProgressVisibility(int visibility);
}
