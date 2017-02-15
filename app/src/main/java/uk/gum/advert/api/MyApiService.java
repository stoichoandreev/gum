package uk.gum.advert.api;

import java.util.Arrays;

import io.reactivex.Observable;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;

/**
 * Created by sniper on 14-Feb-2017.
 * We just have here some mock response to simulate Network response
 */

public class MyApiService implements ApiService {

    @Override
    public Observable<AdvertDetailsResponseParseData> getAdvertDetails(long advertId) {
        final AdvertDetailsResponseParseData advertRawData = new AdvertDetailsResponseParseData();
        //create Object with some test data
        advertRawData.id = advertId;
        advertRawData.title = "BMW X5 for sale";
        advertRawData.price = "20 000 £";
        advertRawData.address = "45 Chivalry Road, London, SW11 1HX";
        advertRawData.date = "02-Feb-2017";
        advertRawData.fuelType = "Diesel";
        advertRawData.contactNumber = "07894503067";
        advertRawData.contactName = "Ishaam";
        advertRawData.description = "BMW of Tuscaloosa presents this BMW Certified Preowned 2013 X5 xDrive35i " +
                "Premium in Vermillion Red Metallic with Oyster Leather Interior. " +
                "Equipped with Rear DVD,Convenience Package, Cold Weather Package, 19\" Alloy Wheels, Power Tailgate, " +
                "Panoramic Roof, Dark Bamboo Wood, Sirius Sat Radio, Heated Front Seats, BMW Apps, iPod Adapter and much more!" +
                "Buy with confidence knowing you'll be covered by the BMW Certified Preowned Warranty. " +
                "Call today to schedule your VIP appointment and take advantage of great finance rates. 205-345-9811";
        advertRawData.images = Arrays.asList("http://autopazar.co.uk/media/10419/Used_Bmw_X5_2010_Grey_4x4_Diesel_Automatic_for_Sale_in_Suffolk_UK.jpg",
                "http://autopazar.co.uk/media/10419/Used_Bmw_X5_Xdrive35d_M_Sport_5_Door_4x4_Grey_2010_Diesel_for_Sale_in_UK.jpg",
                "http://www.carandclassic.co.uk/uploads/cars/bmw/6235481.jpg",
                "http://autopazar.co.uk/media/6383/Used_Bmw_X5_3_0d_Sport_5_Door_Auto_4x4_Black_2010_Diesel_for_Sale_in_UK.jpg",
                "http://malayaliclassifieds.com/images/2012/02/13/51851/2009-bmw-x5-for-sale_2.jpg");
        //return cold observable for subscribers can receive some data
        return Observable.just(advertRawData);
    }
}
