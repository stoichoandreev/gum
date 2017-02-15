package uk.gum.advert.api;

import io.reactivex.Observable;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;

/**
 * Created by sniper on 14-Feb-2017.
 */

public interface ApiService {
    Observable<AdvertDetailsResponseParseData> getAdvertDetails(long advertId);
}
