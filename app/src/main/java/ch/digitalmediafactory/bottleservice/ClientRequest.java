package ch.digitalmediafactory.bottleservice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris Gacu on 26/03/2018.
 */

public class ClientRequest extends StringRequest {

        private static final String LOGIN_REQUEST_URL = "http://bottleservice.ch/BottleserviceAndroidConnection/clientinfo.php";
        private Map<String, String> params;

        public ClientRequest(String userid, Response.Listener<String> listener) {
            super(Method.POST, LOGIN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("userid", userid);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }

}
