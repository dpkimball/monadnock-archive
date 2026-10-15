package com.oxbow.monadnock;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by darin on 11/25/17.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://10.0.2.2:8002/heartleaf/users/register";
    private Map<String,String> params;

    public RegisterRequest(String username, String password, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        //params.put("name", name);
        params.put("username", username);
        params.put("password", password);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
