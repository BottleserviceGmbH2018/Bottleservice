package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 20/04/2018.
 */

public class ClientBookingRecycleview {

    public String locationame;
    public String locationphoto;

    ClientBookingRecycleview(){}

    public ClientBookingRecycleview(String locationame, String locationphoto) {
        this.locationame = locationame;
        this.locationphoto = locationphoto;
    }

    public String getLocationame() {
        return locationame;
    }

    public void setLocationame(String locationame) {
        this.locationame = locationame;
    }

    public String getLocationphoto() {
        return locationphoto;
    }

    public void setLocationphoto(String locationphoto) {
        this.locationphoto = locationphoto;
    }
}
