package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 04/05/2018.
 */

public class LoungeOwnerReservationRecycleview {

    public String locationame;
    public String locationpayment;
    public String locationphoto;
    public String locationtime;
    public String locationtitle;
    public String requested;

    LoungeOwnerReservationRecycleview(){}

    public LoungeOwnerReservationRecycleview(String locationame, String locationpayment, String locationphoto, String locationtime, String locationtitle, String requested) {
        this.locationame = locationame;
        this.locationpayment = locationpayment;
        this.locationphoto = locationphoto;
        this.locationtime = locationtime;
        this.locationtitle = locationtitle;
        this.requested = requested;
    }

    public String getLocationame() {
        return locationame;
    }

    public void setLocationame(String locationame) {
        this.locationame = locationame;
    }

    public String getLocationpayment() {
        return locationpayment;
    }

    public void setLocationpayment(String locationpayment) {
        this.locationpayment = locationpayment;
    }

    public String getLocationphoto() {
        return locationphoto;
    }

    public void setLocationphoto(String locationphoto) {
        this.locationphoto = locationphoto;
    }

    public String getLocationtime() {
        return locationtime;
    }

    public void setLocationtime(String locationtime) {
        this.locationtime = locationtime;
    }

    public String getLocationtitle() {
        return locationtitle;
    }

    public void setLocationtitle(String locationtitle) {
        this.locationtitle = locationtitle;
    }

    public String getRequested() {
        return requested;
    }

    public void setRequested(String requested) {
        this.requested = requested;
    }
}
