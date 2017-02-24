package uk.gum.advert.repos;

import javax.inject.Inject;

import io.reactivex.Observable;
import uk.gum.advert.api.ApiService;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;
import uk.gum.advert.repos.interfaces.IAdvertDetailsRepository;
import uk.gum.advert.utils.RxUtil;

/**
 * Created by sniper on 14-Feb-2017.
 */

public class AdvertDetailsRepository extends BaseRepository implements IAdvertDetailsRepository {

    @Inject
    ApiService apiService;

    @Inject
    public AdvertDetailsRepository() {}

    @Override
    public Observable<AdvertDetailsResponseParseData> requestAdvertData(long advertId) {
        return apiService.getAdvertDetails(advertId)
                .compose(RxUtil.applySchedulers());
//                .observeOn(AndroidSchedulers.mainThread())//return the emit result back on the UI thread because we gonna draw it on the screen
//                .subscribeOn(Schedulers.io());//do the hard work on the io thread (some mapping, some parsing, some conversion and so on)
    }
}
