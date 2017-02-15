package uk.gum.advert.repos.interfaces;

import io.reactivex.Observable;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;

/**
 * Data Repository interface about Advert details screen.
 */
public interface IAdvertDetailsRepository extends IBaseRepository {
    /**
     * Get all details about specific advert
     * @param advertId - the id of the selected advert
     */
    Observable<AdvertDetailsResponseParseData> requestAdvertData(long advertId);
}
