package uk.gum.advert.repos.interfaces;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;
import uk.gum.advert.repos.AdvertDetailsRepository;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by sniper on 22-Feb-2017.
 */
//@RunWith(MockitoJUnitRunner.class)
public class AdvertDetailsRepositoryTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
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
}