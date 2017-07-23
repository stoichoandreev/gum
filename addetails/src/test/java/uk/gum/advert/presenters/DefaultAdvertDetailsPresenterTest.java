package uk.gum.advert.presenters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Single;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.models.AdvertDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAdvertDetailsPresenterTest {

    @Mock
    private ApiService mockApiService;
    @Mock
    private AdvertDetailsPresenter.View mockAdDetailsView;
    @Mock
    private AdvertDetails mockDetailsData;

    private DefaultAdvertDetailsPresenter presenter;

    private final int advertId = 10;

    @Before
    public void setUp() throws Exception {
        //This init line can be skipped if we are using @InjectMocks but not in this case
        presenter = new DefaultAdvertDetailsPresenter(mockAdDetailsView, mockApiService);
    }

    @Test
    public void test_getAdvertDetailsWithoutAd() throws Exception {
        presenter.getAdvertDetails(0);
        //Test
        verify(mockAdDetailsView).onRepositoryErrorOccurred(any());
        verify(mockApiService, never()).getAdvertDetails(0);
    }

    @Test
    public void test_getAdvertDetailsWithAd() throws Exception {
        //Given
        final AdvertDetails data = new AdvertDetails();
        //When
        when(mockApiService.getAdvertDetails(advertId)).thenReturn(Single.just(data));

        presenter.getAdvertDetails(advertId);
        //Test
        verify(mockAdDetailsView).displayAdvertDetails(data);
    }

    @Test
    public void test_getAdvertDetailsWithAdButError() throws Exception {
        //Given
        final Throwable error = new Throwable("some error");
        //When
        when(mockApiService.getAdvertDetails(advertId)).thenReturn(Single.error(error));

        presenter.getAdvertDetails(advertId);
        //Test
        verify(mockAdDetailsView).onRepositoryErrorOccurred(error);
    }

    @Test
    public void test_getAdvertDetailsWithAdForLoadingIndications() throws Exception {
        //Given
        final Single<AdvertDetails> detailsSingle = Single.just(new AdvertDetails());
        //When
        when(mockApiService.getAdvertDetails(advertId)).thenReturn(detailsSingle);

        presenter.getAdvertDetails(advertId);
        //Test
        verify(mockAdDetailsView, times(1)).showLoading();
        verify(mockAdDetailsView, times(1)).hideLoading();

        //Test the order of the methods invocation
        final InOrder inOrder = inOrder(mockAdDetailsView);
        inOrder.verify(mockAdDetailsView).showLoading();
        inOrder.verify(mockAdDetailsView).hideLoading();
    }

    @Test
    public void test_subscribeShareView() throws Exception {
        //Given
        final String titleForShare = "Share title";
        final Observable<Object> shareViewObservable = Observable.just(new Object());

        when(mockDetailsData.getTitle()).thenReturn(titleForShare);
        presenter.setDetailsData(mockDetailsData);
        presenter.subscribeShareView(shareViewObservable);
        //Test
        verify(mockAdDetailsView).handleShareIntent("text/plain", titleForShare);

    }

    @Test
    public void test_subscribeCallView() throws Exception {
        //Given
        final String phoneNumber = "0929392323";
        final Observable<Object> callViewObservable = Observable.just(new Object());

        when(mockDetailsData.getContactNumber()).thenReturn(phoneNumber);
        presenter.setDetailsData(mockDetailsData);
        presenter.subscribeCallView(callViewObservable);
        //Test
        verify(mockAdDetailsView).handleCallIntent(phoneNumber);

    }

    @Test
    public void test_subscribeSMSView() throws Exception {
        //Given
        final String phoneNumber = "0929392323";
        final Observable<Object> smsViewObservable = Observable.just(new Object());

        when(mockDetailsData.getContactNumber()).thenReturn(phoneNumber);
        presenter.setDetailsData(mockDetailsData);
        presenter.subscribeSMSView(smsViewObservable);
        //Test
        verify(mockAdDetailsView).handleSMSIntent(phoneNumber);

    }

    @Test
    public void test_subscribeMessageView() throws Exception {
        //Given
        final Observable<Object> messageViewObservable = Observable.just(new Object());

        presenter.subscribeMessageView(messageViewObservable);
        //Test
        verify(mockAdDetailsView).handleMessageIntent(any());

    }

    @Test
    public void test_getSharedText() throws Exception {
        //Given
        final String titleForShare = "Share title";
        //When
        when(mockDetailsData.getTitle()).thenReturn(titleForShare);
        presenter.setDetailsData(mockDetailsData);
        //Test
        assertThat(presenter.getShareText()).isEqualTo(titleForShare);
    }

    @Test
    public void test_getSharedTextWithoutData() throws Exception {
        presenter.setDetailsData(null);
        //Test
        assertThat(presenter.getShareText()).isEqualTo("Default share text");
    }

}