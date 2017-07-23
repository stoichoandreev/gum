package uk.gum.advert.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.subscribers.TestSubscriber;
import uk.gum.advert.models.AdDetails;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(PowerMockRunner.class)
@PrepareForTest({
        RxUtil.class
})
public class RxUtilTest {

    @Test
    public void test_applySchedulersForNoneNull() throws Exception {
        final SingleTransformer<AdDetails, AdDetails> singleTransformer = RxUtil.applySchedulers();
        assertThat(singleTransformer).isNotNull();
    }

    @Test
    public void test_applySchedulers() throws Exception {
        final Flowable<AdDetails> adDetailsFlowable = Single.just(new AdDetails())
                .compose(RxUtil.applySchedulers())
                .toFlowable();
        final TestSubscriber testSubscriber = new TestSubscriber<AdDetails>() {
            @Override
            public void onSubscribe(Subscription s) {
                super.onSubscribe(s);
                String threadName = Thread.currentThread().getName();
                assertThat(threadName).startsWith("main");
            }

            @Override
            public void onNext(AdDetails details) {
                String threadName = Thread.currentThread().getName();
                assertThat(threadName).startsWith("RxCachedThreadScheduler");
                super.onNext(details);
            }
        };
        adDetailsFlowable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
    }
}