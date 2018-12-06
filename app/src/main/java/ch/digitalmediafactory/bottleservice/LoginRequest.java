package ch.digitalmediafactory.bottleservice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris Gacu on 05/03/2018.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://bottleservice.ch/BottleserviceAndroidConnection/Login.php";
    private Map<String, String> params;

    public LoginRequest(String userid, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("userid", userid);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
