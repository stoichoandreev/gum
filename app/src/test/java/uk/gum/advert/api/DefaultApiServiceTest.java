package uk.gum.advert.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import uk.gum.advert.Settings;
import uk.gum.advert.models.AdDetails;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
public class DefaultApiServiceTest {

    @Mock
    private Settings mockSettings;

    private DefaultApiService testedApiService;

    @Before
    public void setUp() throws Exception {
        testedApiService = new DefaultApiService(mockSettings);
    }

    @Test
    public void test_getAdvertDetails() throws Exception {
        //Given -> When
        Single<AdDetails> data = testedApiService.getAdDetails(20);
        //Test
        assertThat(data).isNotNull();
    }

    @Test
    public void test_dataInsideTheObservable() throws Exception {
        //When
        Single<AdDetails> data = testedApiService.getAdDetails(20);
        //Test
        data.observeOn(AndroidSchedulers.mainThread()).subscribe(getSingleValidator());
    }

    private SingleObserver<AdDetails> getSingleValidator() {
        return new SingleObserver<AdDetails>() {

            @Override
            public void onSubscribe(Disposable d) {
                assertThat(d).isNotNull();
            }

            @Override
            public void onSuccess(AdDetails value) {
                assertThat(value.getAddress()).isEqualTo("45 Chivalry Road, London, SW11 1HX");
            }

            @Override
            public void onError(Throwable e) {
                //unused
            }
        };
    }
}