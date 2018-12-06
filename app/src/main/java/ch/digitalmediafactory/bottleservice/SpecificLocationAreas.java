package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 10/04/2018.
 */

public class SpecificLocationAreas {
    public SpecificLocationAreas(){}

    public String locationTitle;
    public String specficationSlider;
    public String specficationPrice;
    public String specficationGuests;
    public String specficationDescription;

    public SpecificLocationAreas(String specficationTitle, String specficationSlider, String specficationPrice, String specficationGuests, String specficationDescription) {
        this.locationTitle = specficationTitle;
        this.specficationSlider = specficationSlider;
        this.specficationPrice = specficationPrice;
        this.specficationGuests = specficationGuests;
        this.specficationDescription = specficationDescription;
    }


    public String getlocationTitle() {
        return locationTitle;
    }

    public void setlocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getSpecficationSlider() {
        return specficationSlider;
    }

    public void setSpecficationSlider(String specficationSlider) {
        this.specficationSlider = specficationSlider;
    }

    public String getSpecficationPrice() {
        return specficationPrice;
    }

    public void setSpecficationPrice(String specficationPrice) {
        this.specficationPrice = specficationPrice;
    }

    public String getSpecficationGuests() {
        return specficationGuests;
    }

    public void setSpecficationGuests(String specficationGuests) {
        this.specficationGuests = specficationGuests;
    }

    public String getSpecficationDescription() {
        return specficationDescription;
    }

    public void setSpecficationDescription(String specficationDescription) {
        this.specficationDescription = specficationDescription;
    }
}
