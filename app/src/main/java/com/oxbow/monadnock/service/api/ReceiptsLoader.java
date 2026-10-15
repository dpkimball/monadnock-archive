package com.oxbow.monadnock.service.api;

import android.content.Context;
import android.util.Log;

import com.oxbow.monadnock.service.TwinPeaksApi;
import com.oxbow.monadnock.service.domain.Receipt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Loader to execute and parse beacons.list
 */
public class ReceiptsLoader extends BaseApiLoader<List<Receipt>> {
    private static final String TAG = ReceiptsLoader.class.getSimpleName();

    public ReceiptsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartRequest() {
        TwinPeaksApi.getInstance(getContext()).getReceiptsList(this, this);
    }

    @Override
    protected List<Receipt> onHandleResponse(JSONObject response) {
        //API Error
        if (response == null) return null;

        try {
            return Receipt.fromJson(response);
        } catch (JSONException e) {
            Log.w(TAG, "Parsing error", e);
            return null;
        }
    }
}
