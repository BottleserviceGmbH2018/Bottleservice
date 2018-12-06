package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 30/04/2018.
 */

public class OwnerApprovalsRecycleview {

    public String locationame;
    public String locationphoto;
    public String locationid;
    public String locationtype;
    public String request_type;


    OwnerApprovalsRecycleview(){}

    public OwnerApprovalsRecycleview(String locationame, String locationphoto, String locationid, String locationtype, String request_type) {
        this.locationame = locationame;
        this.locationphoto = locationphoto;
        this.locationid = locationid;
        this.locationtype = locationtype;
        this.request_type = request_type;
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

    public String getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }
}
