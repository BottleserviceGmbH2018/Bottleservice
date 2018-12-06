package ch.digitalmediafactory.bottleservice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris Gacu on 12/03/2018.
 */

public class RegisterRequestPersonal extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://bottleservice.ch/BottleserviceAndroidConnection/RegisterPersonal.php";
    private Map<String, String> params;


    public RegisterRequestPersonal(String firstname, String middlename, String lastname,  String email, String password, String date, int user_type, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("firstname" , firstname);
        params.put("middlename" , middlename);
        params.put("lastname" , lastname);
        params.put("password" , password);
        params.put("email" , email);
        params.put("user_type" , user_type + "");
        params.put("date" , date);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}