package uk.gum.advert.ui;


import android.support.v7.app.AppCompatActivity;

import uk.gum.advert.mvp.Presenter;
import uk.gum.advert.dagger.ComponentsManager;
import uk.gum.advert.dagger.components.BaseComponent;

public abstract class BaseActivity<P extends Presenter, C extends BaseComponent> extends AppCompatActivity {

    private P mPresenter;

    protected abstract C getScreenComponent();
    protected abstract String getComponentKey();

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(getComponentKey());
            mPresenter.destroy();
        }
        super.onDestroy();
    }

    protected void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

}
