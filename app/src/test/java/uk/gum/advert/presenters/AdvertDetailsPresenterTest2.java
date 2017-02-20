package uk.gum.advert.presenters;

import android.content.Intent;
import android.net.Uri;

import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.gum.advert.models.AdvertDetailsViewData;
import uk.gum.advert.presenters.interfaces.IAdvertDetailsPresenter;
import uk.gum.advert.repos.interfaces.IAdvertDetailsRepository;
import uk.gum.advert.ui.views.AdvertDetailsView;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.mock;


/**
 * Created by sniper on 15-Feb-2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class})
public class AdvertDetailsPresenterTest2 {

    @Mock
    private IAdvertDetailsRepository advertDetailsRepository;
    @Mock
    private AdvertDetailsView advertDetailsView;
//    @InjectMocks - NOTE we can't use InjectMocks because we are using IAdvertDetailsPresenter(interface) not AdvertDetailsPresenter
    private IAdvertDetailsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        //This init line can be skipped if we are using @InjectMocks but not in this case
        presenter = new AdvertDetailsPresenter(advertDetailsView, advertDetailsRepository);
    }

    @Test
    public void resolveShareAction() throws Exception {
        //If we send action action = null and type = null we should receive null for the Intent
        Assert.assertEquals(presenter.resolveShareAction(null, null), null);
        //TODO Need more work to test the share intent
    }

    @Test
    public void testResolvePhoneNumberAction() throws Exception {
        //If we send action action = null and phoneAction = null we should receive null for the Intent
        Assert.assertEquals(presenter.resolvePhoneNumberAction(null, null), null);

        //If we send action action = Intent.ACTION_CALL and phoneAction = AdvertDetailsPresenter.TYPE_CALL we should receive Intent about phone call
        final String somePhoneNumber = "09899883939";
        AdvertDetailsViewData data = new AdvertDetailsViewData.Builder()
                .contactNumber(somePhoneNumber)
                .build();
        presenter.setDetailsData(data);
        PowerMockito.mockStatic(Uri.class);
        Uri uri = mock(Uri.class);

        PowerMockito.when(Uri.class, "parse", anyString()).thenReturn(uri);
        final Intent callIntent = presenter.resolvePhoneNumberAction(Intent.ACTION_CALL, AdvertDetailsPresenter.TYPE_CALL);
        Assert.assertEquals(callIntent.getData(), Uri.parse(AdvertDetailsPresenter.TYPE_CALL + presenter.getPhoneNumber()));
    }
}