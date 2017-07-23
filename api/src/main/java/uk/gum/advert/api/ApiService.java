package uk.gum.advert.api;

import io.reactivex.Single;
import uk.gum.advert.models.AdDetails;


public interface ApiService {
    /**
     * Get advert details
     * @param advertId - advert id
     * @return - Observable/Single to emit AdvertDetails data
     */
    Single<AdDetails> getAdDetails(long advertId);
}
