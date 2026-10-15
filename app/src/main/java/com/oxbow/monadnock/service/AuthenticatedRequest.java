package com.oxbow.monadnock.service;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class AuthenticatedRequest extends JsonObjectRequest {

    private String mAuthToken;


    public AuthenticatedRequest(int method, String url, JSONObject jsonRequest,
                                Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public  void setAuthToken(String token) {
        mAuthToken = token;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject jsonResponse = jsonString.trim().isEmpty() ? new JSONObject() : new JSONObject(jsonString);

            // set token after receiving from login response
            //TokenHandler.setToken(response.headers.get("x-auth"));

            return Response.success(jsonResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        if (mAuthToken != null && !mAuthToken.isEmpty()) {
            headers.put("Authorization", mAuthToken.startsWith("Bearer ") ? mAuthToken : "Bearer " + mAuthToken);
        }
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }
}
