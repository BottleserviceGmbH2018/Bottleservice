package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 28/06/2018.
 */

public class LocationSearchRecycleview {

    public String locationame;
    public String locationphoto;
    public String locationid;
    public int locationtime;
    public String locationtype;

    LocationSearchRecycleview() {
    }

    public LocationSearchRecycleview(String locationame, String locationphoto, String locationid, int locationtime, String locationtype) {
        this.locationame = locationame;
        this.locationphoto = locationphoto;
        this.locationid = locationid;
        this.locationtime = locationtime;
        this.locationtype = locationtype;
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

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public int getLocationtime() {
        return locationtime;
    }

    public void setLocationtime(int locationtime) {
        this.locationtime = locationtime;
    }

    public String getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }




}
