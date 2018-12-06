package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 03/05/2018.
 */

public class OwnerApprovalsSpecificRecycleview {

    public String locationspecificarea;
    public String locationspecificguest;
    public String locationspecificphoto;
    public String locationspecificprice;
    public String locationspecifictitle;

    OwnerApprovalsSpecificRecycleview(){}


    public OwnerApprovalsSpecificRecycleview(String locationspecificarea, String locationspecificguest, String locationspecificphoto, String locationspecificprice, String locationspecifictitle) {
        this.locationspecificarea = locationspecificarea;
        this.locationspecificguest = locationspecificguest;
        this.locationspecificphoto = locationspecificphoto;
        this.locationspecificprice = locationspecificprice;
        this.locationspecifictitle = locationspecifictitle;
    }

    public String getLocationspecificarea() {
        return locationspecificarea;
    }

    public void setLocationspecificarea(String locationspecificarea) {
        this.locationspecificarea = locationspecificarea;
    }

    public String getLocationspecificguest() {
        return locationspecificguest;
    }

    public void setLocationspecificguest(String locationspecificguest) {
        this.locationspecificguest = locationspecificguest;
    }

    public String getLocationspecificphoto() {
        return locationspecificphoto;
    }

    public void setLocationspecificphoto(String locationspecificphoto) {
        this.locationspecificphoto = locationspecificphoto;
    }

    public String getLocationspecificprice() {
        return locationspecificprice;
    }

    public void setLocationspecificprice(String locationspecificprice) {
        this.locationspecificprice = locationspecificprice;
    }

    public String getLocationspecifictitle() {
        return locationspecifictitle;
    }

    public void setLocationspecifictitle(String locationspecifictitle) {
        this.locationspecifictitle = locationspecifictitle;
    }
}
