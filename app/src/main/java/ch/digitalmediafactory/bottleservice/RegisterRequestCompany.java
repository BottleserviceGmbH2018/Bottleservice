package ch.digitalmediafactory.bottleservice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris Gacu on 05/03/2018.
 */

public class RegisterRequestCompany extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://bottleservice.ch/BottleserviceAndroidConnection/RegisterCompany.php";
    private Map<String, String> params;


    public RegisterRequestCompany(String companyname, String companyownername, String companyemail, String companypassword, String companyaddress, String companyaddress2, String companycity, String companypostal,
                                  String companyphonenumber, String companyownerphonenumber, String companyownermobilenumber, int user_type, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_type" , user_type + "");
        params.put("companyname" , companyname);
        params.put("companyownername" , companyownername);
        params.put("companyemail" , companyemail);
        params.put("companypassword" , companypassword);
        params.put("companyaddress" , companyaddress);
        params.put("companyaddress2" , companyaddress2);
        params.put("companycity" , companycity);
        params.put("companypostal" , companypostal);
        params.put("companyphonenumber" , companyphonenumber);
        params.put("companyownerphonenumber" , companyownerphonenumber);
        params.put("companyownermobilenumber" , companyownermobilenumber);




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

