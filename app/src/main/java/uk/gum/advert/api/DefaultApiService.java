package uk.gum.advert.api;

import java.util.Arrays;

import io.reactivex.Single;
import uk.gum.advert.Settings;
import uk.gum.advert.models.AdDetails;
import uk.gum.advert.utils.RxUtil;

/**
 * We just have here some mock response to simulate Network request
 * In the real world this could be done with Retrofit 2
 */

public class DefaultApiService implements ApiService {

    private Settings settings; //unused

    public DefaultApiService(Settings setting) {
        this.settings = setting;
    }

    @Override
    public Single<AdDetails> getAdDetails(long adId) {
        final AdDetails advertRawData = new AdDetails();

        advertRawData.setId(adId);
        advertRawData.setTitle("BMW X5 for sale");
        advertRawData.setPrice("25 000 £");
        advertRawData.setAddress("45 Chivalry Road, London, SW11 1HX");
        advertRawData.setDate("02-Feb-2017");
        advertRawData.setFuelType("Diesel");
        advertRawData.setContactNumber("07894503067");
        advertRawData.setContactName("Andrey");
        advertRawData.setDescription("BMW of Tuscaloosa presents this BMW Certified Preowned 2013 X5 xDrive35i " +
                "Premium in Vermillion Red Metallic with Oyster Leather Interior. " +
                "Equipped with Rear DVD,Convenience Package, Cold Weather Package, 19\" Alloy Wheels, Power Tailgate, " +
                "Panoramic Roof, Dark Bamboo Wood, Sirius Sat Radio, Heated Front Seats, BMW Apps, iPod Adapter and much more!" +
                "Buy with confidence knowing you'll be covered by the BMW Certified Preowned Warranty. " +
                "Call today to schedule your VIP appointment and take advantage of great finance rates. 205-345-9811");

        advertRawData.setImages(Arrays.asList("http://autopazar.co.uk/media/10419/Used_Bmw_X5_2010_Grey_4x4_Diesel_Automatic_for_Sale_in_Suffolk_UK.jpg",
                "http://autopazar.co.uk/media/10419/Used_Bmw_X5_Xdrive35d_M_Sport_5_Door_4x4_Grey_2010_Diesel_for_Sale_in_UK.jpg",
                "http://www.carandclassic.co.uk/uploads/cars/bmw/6235481.jpg",
                "http://autopazar.co.uk/media/6383/Used_Bmw_X5_3_0d_Sport_5_Door_Auto_4x4_Black_2010_Diesel_for_Sale_in_UK.jpg",
                "http://malayaliclassifieds.com/images/2012/02/13/51851/2009-bmw-x5-for-sale_2.jpg"));

        return Single
                .just(advertRawData)
                .compose(RxUtil.applySchedulers());
    }
}
