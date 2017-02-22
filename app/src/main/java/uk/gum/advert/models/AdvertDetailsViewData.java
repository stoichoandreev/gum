package uk.gum.advert.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.gum.advert.BR;
import uk.gum.advert.R;
import uk.gum.advert.api.pojos.producer_details.AdvertDetailsResponseParseData;


/**
 * Created by sniper on 02/14/17.
 * This Data class is example how API data model can be wrapped to View Data model with observable,
 * so every time when data model change the view will be updated
 */

public class AdvertDetailsViewData extends BaseObservable {

    private static final String DEFAULT_VALUE = "...";

    public long id;
    public String title;
    public String price;
    public String address;
    public String date;
    public String fuelType;
    public String contactNumber;
    public String contactName;
    public String description;
    public boolean enableContactButtons;
    public String image;//this field will hold the current image , presented on the screen
    public List<String> images;

    private AdvertDetailsViewData(Builder builder) {
        id = builder.id;
        title = builder.title != null ? builder.title : DEFAULT_VALUE;
        price = builder.price != null ? builder.price : DEFAULT_VALUE;
        address = builder.address != null ? builder.address : DEFAULT_VALUE;
        date = builder.date != null ? builder.date : DEFAULT_VALUE;
        fuelType = builder.fuelType != null ? builder.fuelType : DEFAULT_VALUE;
        contactNumber = builder.contactNumber != null ? builder.contactNumber : DEFAULT_VALUE;
        enableContactButtons = builder.contactNumber != null;
        contactName = builder.contactName != null ? builder.contactName : DEFAULT_VALUE;
        description = builder.description != null ? builder.description : DEFAULT_VALUE;
        images = builder.images;
        image = images != null && images.size() > 0 ? images.get(0) : DEFAULT_VALUE;
    }

    /**
     * Use this method to update the View when the new fresh data come from API
     * @param rawAdvertData - the API parse data
     */

    public static AdvertDetailsViewData fromApiResponse(AdvertDetailsResponseParseData rawAdvertData) {
        if (rawAdvertData == null) return null;
        return new Builder()
                .id(rawAdvertData.id)
                .title(rawAdvertData.title)
                .price(rawAdvertData.price)
                .address(rawAdvertData.address)
                .date(rawAdvertData.date)
                .fuelType(rawAdvertData.fuelType)
                .contactNumber(rawAdvertData.contactNumber)
                .contactName(rawAdvertData.contactName)
                .description(rawAdvertData.description)
                .images(rawAdvertData.images)
                .build();
    }

    public static final class Builder {
        public long id;
        public String title;
        public String price;
        public String address;
        public String date;
        public String fuelType;
        public String contactNumber;
        public String contactName;
        public String description;
        public List<String> images;

        public Builder() {
        }

        public AdvertDetailsViewData.Builder id(long val) {
            id = val;
            return this;
        }
        public AdvertDetailsViewData.Builder title(String val) {
            title = val;
            return this;
        }

        public AdvertDetailsViewData.Builder price(String val) {
            price = val;
            return this;
        }
        public AdvertDetailsViewData.Builder address(String val) {
            address = val;
            return this;
        }
        public AdvertDetailsViewData.Builder date(String val) {
            date = val;
            return this;
        }
        public AdvertDetailsViewData.Builder fuelType(String val) {
            fuelType = val;
            return this;
        }
        public AdvertDetailsViewData.Builder contactNumber(String val) {
            contactNumber = val;
            return this;
        }
        public AdvertDetailsViewData.Builder contactName(String val) {
            contactName = val;
            return this;
        }
        public AdvertDetailsViewData.Builder description(String val) {
            description = val;
            return this;
        }
        public AdvertDetailsViewData.Builder images(List<String> val) {
            images = val;
            return this;
        }

        public AdvertDetailsViewData build() {
            return new AdvertDetailsViewData(this);
        }
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }
    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }
    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
    @Bindable
    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
        notifyPropertyChanged(BR.fuelType);
    }
    @Bindable
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        notifyPropertyChanged(BR.contactNumber);
    }

    @Bindable
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
        notifyPropertyChanged(BR.contactName);
    }
    @Bindable
    public boolean isEnableContactButtons() {
        return enableContactButtons;
    }

    public void setEnableContactButtons(boolean enableContactButtons) {
        this.enableContactButtons = enableContactButtons;
        notifyPropertyChanged(BR.enableContactButtons);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
    @Bindable
    public List<String> getImages() {
        return images;
    }

    //Annotated Binding adapter method must be static
    @BindingAdapter({"bind:image"})
    public static void setImage(ImageView view, String imageUrl) {
        if(imageUrl != null) {
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(view);
        } else {
            view.setBackgroundResource(R.drawable.placeholder);
        }
    }
}
