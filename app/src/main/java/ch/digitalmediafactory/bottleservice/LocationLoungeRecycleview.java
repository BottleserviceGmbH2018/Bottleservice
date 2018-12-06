package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 11/04/2018.
 */

public class LocationLoungeRecycleview {


    LocationLoungeRecycleview() {
    }

    public String locationTitle;
    public String locationSlider;
    public String locationMap;
    public String ownerUserID;

    public LocationLoungeRecycleview(String locationTitle, String locationSlider, String locationMap, String ownerUserID) {
        this.locationTitle = locationTitle;
        this.locationSlider = locationSlider;
        this.locationMap = locationMap;
        this.ownerUserID = ownerUserID;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getLocationSlider() {
        return locationSlider;
    }

    public void setLocationSlider(String locationSlider) {
        this.locationSlider = locationSlider;
    }

    public String getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(String locationMap) {
        this.locationMap = locationMap;
    }

    public String getOwnerUserID() {
        return ownerUserID;
    }

    public void setOwnerUserID(String ownerUserID) {
        this.ownerUserID = ownerUserID;
    }
}