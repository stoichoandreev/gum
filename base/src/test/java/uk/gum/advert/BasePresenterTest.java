package uk.gum.advert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import uk.gum.advert.mvp.BasePresenterView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CompositeDisposable.class) //final class can be tested with PowerMockRunner
public class BasePresenterTest {
    @Mock
    private BasePresenterView mockView;

    private TestPresenter testedPresenter;

    @Before
    public void setUp() throws Exception {
        testedPresenter = new TestPresenter(mockView);
    }

    @Test
    public void test_basePresenterView() throws Exception {
        //When -> Test
        assertThat(testedPresenter.mView).isEqualTo(mockView);
    }

    @Test
    public void test_compositeDisposableInitialization() throws Exception {
        //When -> Test
        assertThat(testedPresenter.getCompositeDisposable()).isNotEqualTo(null);
    }

    @Test
    public void test_addingDisposableWithNull() throws Exception {
        //When
        testedPresenter.addDisposable(null);
        //Test
        assertThat(testedPresenter.getCompositeDisposable().size()).isEqualTo(0);
    }

    @Test
    public void test_addingDisposable() throws Exception {
        //Given
        final Disposable disposable1 = Mockito.mock(Disposable.class);
        final Disposable disposable2 = Mockito.mock(Disposable.class);
        //When
        testedPresenter.addDisposable(disposable1);
        testedPresenter.addDisposable(disposable2);
        //Test
        assertThat(testedPresenter.getCompositeDisposable().size()).isEqualTo(2);
    }

    @Test
    public void test_destroyingDisposable() throws Exception {
        //Given
        TestPresenter spyPresenter = Mockito.spy(testedPresenter);
        CompositeDisposable spyDisposableComposition = PowerMockito.spy(testedPresenter.getCompositeDisposable());
        //When
        when(spyPresenter.getCompositeDisposable()).thenReturn(spyDisposableComposition);
        spyPresenter.clearAllDisposables();
        //Test
        verify(spyPresenter.getCompositeDisposable(), times(1)).dispose();
    }

    @Test
    public void test_destroyingDisposableWithNullComposition() throws Exception {
        //Given
        TestPresenter spyPresenter = Mockito.spy(testedPresenter);
        //When
        when(spyPresenter.getCompositeDisposable()).thenReturn(null);
        //Test
        verify(spyPresenter, never()).clearAllDisposables();
    }

    //Just a test implementation of BasePresenter for the purpose of these Tests
    private class TestPresenter extends BasePresenter<BasePresenterView> {
        private TestPresenter(BasePresenterView view) {
            super(view);
        }
    }
}