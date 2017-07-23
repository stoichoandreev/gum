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
import uk.gum.advert.models.AdvertDetails;
import uk.gum.advert.repos.AdvertDetailsRepository;
import uk.gum.advert.repos.AdvertAPI;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdvertDetailsRepositoryTest {
    @Mock
    private ApiService apiService;

    private AdvertAPI repository;

    @Before
    public void setUp() throws Exception {
        //If we like, we can test with some other IAdvertDetailsRepository implementation
        repository = new AdvertDetailsRepository();
    }

    @Test
    public void testRequestAdvertData() throws Exception {
        //stub some apiService methods because apiService is mock not real instance
        when(apiService.getAdvertDetails(10)).thenReturn(Observable.just(new AdvertDetails()));
        //call the repository method which we are testing
        repository.requestAdvertData(10);
        //Finally Verifying that the getAdvertDetails method was indeed invoked
        Mockito.verify(apiService, times(1)).getAdvertDetails(10);
    }
    @Test
    public void testAdvertDetailsObservable() throws Exception {
        AdvertDetails testData = new AdvertDetails();
        TestSubscriber<AdvertDetails> testSubscriber = new TestSubscriber<>();
        repository.requestAdvertData(10).subscribe((Observer<? super AdvertDetails>) testSubscriber);
        testSubscriber.assertResult(testData);
    }
}