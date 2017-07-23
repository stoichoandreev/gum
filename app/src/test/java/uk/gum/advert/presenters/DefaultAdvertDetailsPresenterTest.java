package uk.gum.advert.presenters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import uk.gum.advert.models.AdvertDetails;
import uk.gum.advert.view_models.AdvertDetailsViewData;
import uk.gum.advert.repos.AdvertAPI;
import uk.gum.advert.ui.views.AdvertDetailsView;

import static org.easymock.EasyMock.mock;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by sniper on 15-Feb-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultAdvertDetailsPresenterTest {

    @Mock
    private AdvertAPI advertDetailsRepository;
    @Mock
    private AdvertDetailsView advertDetailsView;

//    @InjectMocks - NOTE we can't use InjectMocks because we are using IAdvertDetailsPresenter(interface) not AdvertDetailsPresenter
    private AdvertDetailsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        //This init line can be skipped if we are using @InjectMocks but not in this case
        presenter = new DefaultAdvertDetailsPresenter(advertDetailsView, advertDetailsRepository);
    }

    @Test
    public void testGetAdvertDetailsById() throws Exception {
        //Test does advertDetailsView.onAdvertDetailsReady is called if repository return correct answer
        final int advertId = 10;
        when(advertDetailsRepository.requestAdvertData(advertId)).thenReturn(Observable.just(new AdvertDetails()));
        presenter.getAdvertDetailsById(advertId);
        verify(advertDetailsView).onAdvertDetailsReady(any());

        //Test does advertDetailsView.onRepositoryErrorOccurred is called if repository return error
        final Throwable error = new Throwable("some error");
        when(advertDetailsRepository.requestAdvertData(advertId)).thenReturn(Observable.error(error));
        presenter.getAdvertDetailsById(advertId);
        verify(advertDetailsView).onRepositoryErrorOccurred(any());
        verify(advertDetailsView).onRepositoryErrorOccurred(error);
    }

    @Test
    public void testResolveMessageAction() throws Exception {
        //We don't have implementation for Message Action that's why should return null
        Assert.assertThat(presenter.resolveMessageAction(), is(nullValue()));
    }

    @Test
    public void testGetShareText() throws Exception {
        //first with without details data (Should return default text)
        Assert.assertThat(((DefaultAdvertDetailsPresenter)presenter).getShareText(), is("Default share text"));

        //then with available text for share (Should return the actual advert title)
        final String someTitle = "My mock title";
        AdvertDetailsViewData data = new AdvertDetailsViewData.Builder()
                .title(someTitle)
                .build();
        ((DefaultAdvertDetailsPresenter)presenter).setDetailsData(data);
        Assert.assertThat(((DefaultAdvertDetailsPresenter)presenter).getShareText(), is(someTitle));
    }

    @Test
    public void testGetPhoneNumber() throws Exception {
        //test if we don't have any details data stored inside the presenter (Should return null)
        Assert.assertThat(((DefaultAdvertDetailsPresenter)presenter).getPhoneNumber(), is(nullValue()));

        //test when we have details data and some phone number inside (should return the actual phone number)
        final String somePhoneNumber = "09899883939";
        AdvertDetailsViewData data = new AdvertDetailsViewData.Builder()
                .contactNumber(somePhoneNumber)
                .build();
        ((DefaultAdvertDetailsPresenter)presenter).setDetailsData(data);
        Assert.assertThat(((DefaultAdvertDetailsPresenter)presenter).getPhoneNumber(), is(somePhoneNumber));
    }

    @Test
    public void testGetAndSetDetailsData() throws Exception {
        //Set Details data
        AdvertDetailsViewData data = mock(AdvertDetailsViewData.class);
        ((DefaultAdvertDetailsPresenter)presenter).setDetailsData(data);
        //We should receive the same mock data when we try to get it back
        Assert.assertThat(((DefaultAdvertDetailsPresenter)presenter).getDetailsData(), is(data));
    }
}