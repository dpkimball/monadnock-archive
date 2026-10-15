package com.oxbow.monadnock.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.oxbow.monadnock.service.domain.Receipt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TwinPeaksApi {

    private static final String TAG = TwinPeaksApi.class.getSimpleName();

    private static TwinPeaksApi sInstance;

    public static synchronized TwinPeaksApi getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TwinPeaksApi(context.getApplicationContext());
        }

        return sInstance;
    }


    private static String TWINPEAKS_HOST = "http://10.0.2.2:8090";
    private static String LOGIN_URL = "login";
    private static String RECEIPTS_URL = "Receipts";
    private static String SIGN_UP_URL = "users/sign-up";
    private static String VERSION = "v1";

    private RequestQueue mRequestQueue;
    private HashSet<ApiDataCallback> mCallbacks;
    private String mAuthToken;

    private TwinPeaksApi(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.start();
        mCallbacks = new HashSet<>();
    }

    public void setAuthToken(String token) {
        mAuthToken = token;
    }

    public boolean hasToken() {
        return !TextUtils.isEmpty(mAuthToken);
    }

    /* Callback Handlers */
    public boolean registerDataCallback(ApiDataCallback callback) {
        return mCallbacks.add(callback);
    }

    public boolean unregisterDataCallback(ApiDataCallback callback) {
        return mCallbacks.remove(callback);
    }

    /*public TwinPeaks(File directory) {

        // Instantiate the cache
        Cache cache = new DiskBasedCache(directory, 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        queue = new RequestQueue(cache, network);
        queue.start();
    }*/

    private static JSONObject _make_cred_object(String mUsername, String mPassword) {
        JSONObject js = new JSONObject();
        try {
            js.put("username", mUsername);
            js.put("password", mPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js;
    }

    private void performPostRequest(String endpoint, JSONObject body,
                                    Response.Listener<JSONObject> listener,
                                    Response.ErrorListener errorListener) {
        AuthenticatedRequest request = new AuthenticatedRequest(
                Request.Method.POST, endpoint, body, listener, errorListener);
        request.setAuthToken(mAuthToken);
        mRequestQueue.add(request);
    }

    private void performDeleteRequest(String endpoint,
                                      Response.Listener<JSONObject> listener,
                                      Response.ErrorListener errorListener) {
        AuthenticatedRequest request = new AuthenticatedRequest(
                Request.Method.DELETE, endpoint, null, listener, errorListener);
        request.setAuthToken(mAuthToken);
        mRequestQueue.add(request);
    }

    private void performGetRequest(String endpoint,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener) {
        AuthenticatedRequest request = new AuthenticatedRequest(
                Request.Method.GET, endpoint, null, listener, errorListener);
        request.setAuthToken(mAuthToken);
        mRequestQueue.add(request);
    }

    /**
     * receipts.list
     * Return a list of all receipts in this API project
     */
    public void getReceiptsList(Response.Listener<JSONObject> listener,
                                Response.ErrorListener errorListener) {
        final String endpoint = String.format("%s/%s/%s", TWINPEAKS_HOST, VERSION, RECEIPTS_URL);
        Log.v(TAG, "Getting Receipts: " + endpoint);
        performGetRequest(endpoint, listener, errorListener);
    }

    public void deleteReceipt(Receipt receipt) {
        ReceiptDataCallback callback = new ReceiptDataCallback();
        //Attachment name contains beacon name
        final String endpoint = String.format("%s/%s/%s/%s", TWINPEAKS_HOST, VERSION, RECEIPTS_URL, receipt.id);
        Log.v(TAG, "Deleting receipt: " + endpoint);
        performDeleteRequest(endpoint, callback, callback);
    }

    private class ReceiptDataCallback implements
            Response.Listener<JSONObject>, Response.ErrorListener {
        @Override
        public void onResponse(JSONObject response) {
            Log.v(TAG, "Receipt Request Completed");
            notifyListeners();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.w(TAG, "Receipt API Error", error);
            notifyListeners();
        }

        private void notifyListeners() {
            for (ApiDataCallback callback : mCallbacks) {
                callback.onAttachmentResponse();
            }
        }
    }

    public void add_login(String mUsername, String mPassword, final Response.Listener<JSONObject> listener) {
        JSONObject js = _make_cred_object(mUsername, mPassword);
        final String endpoint = String.format("%s/%s", TWINPEAKS_HOST, LOGIN_URL);
        // Make request for JSONObject
        Log.v(TAG, "Logging in: " + endpoint);
        JsonObjectRequest logRequest = new JsonObjectRequest(
                Request.Method.POST, endpoint, js, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;
                    //todo: refactor
                    TokenHandler.setToken(response.headers.get("Authorization"));
                    setAuthToken(response.headers.get("Authorization"));

                    if (jsonString != null && jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    System.err.println("Error = " + je.toString());
                    return Response.error(new ParseError(je));
                }
            }
        };

        mRequestQueue.add(logRequest);
    }

    public void add_create_user(final String mUsername, final String mPassword, final Response.Listener<JSONObject> listener) {

        JSONObject js = _make_cred_object(mUsername, mPassword);
        final String endpoint = String.format("%s/%s", TWINPEAKS_HOST, SIGN_UP_URL);
        // Make request for JSONObject
        Log.v(TAG, "Creating User: " + endpoint);
        JsonObjectRequest signUpRequest = new JsonObjectRequest(
                Request.Method.POST, endpoint, js, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    if (response.statusCode == HttpURLConnection.HTTP_CREATED || response.statusCode == HttpURLConnection.HTTP_OK) {
                        add_login(mUsername, mPassword, listener);
                    }

                    JSONObject result = null;

                    if (jsonString != null && jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    System.err.println("Error = " + je.toString());
                    return Response.error(new ParseError(je));
                }
            }
        };

        mRequestQueue.add(signUpRequest);
    }
}
