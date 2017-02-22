package uk.gum.advert.repos.interfaces;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subscribers.TestSubscriber;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.api.MyApiService;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;
import uk.gum.advert.repos.AdvertDetailsRepository;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by sniper on 22-Feb-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdvertDetailsRepositoryTest {
    @Mock
    private ApiService apiService;

    private IAdvertDetailsRepository repository;

    @Before
    public void setUp() throws Exception {
        //If we like, we can test with some other IAdvertDetailsRepository implementation
        repository = new AdvertDetailsRepository();
    }

    @Test
    public void testRequestAdvertData() throws Exception {
        //stub some apiService methods because apiService is mock not real instance
        when(apiService.getAdvertDetails(10)).thenReturn(Observable.just(new AdvertDetailsResponseParseData()));
        //call the repository method which we are testing
        repository.requestAdvertData(10);
        //Finally Verifying that the getAdvertDetails method was indeed invoked
        Mockito.verify(apiService, times(1)).getAdvertDetails(10);
    }
    @Test
    public void testAdvertDetailsObservable() throws Exception {
        AdvertDetailsResponseParseData testData = new AdvertDetailsResponseParseData();
        TestSubscriber<AdvertDetailsResponseParseData> testSubscriber = new TestSubscriber<>();
        repository.requestAdvertData(10).subscribe((Observer<? super AdvertDetailsResponseParseData>) testSubscriber);
        testSubscriber.assertResult(testData);
    }
}