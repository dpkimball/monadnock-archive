package com.oxbow.monadnock.service.domain;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Model representation of a beacons.attachments resource
 */
public class Receipt {
    private static final String TAG = "Receipt";

    public static List<Receipt> fromJson(JSONObject object) throws JSONException {
        ArrayList<Receipt> items = new ArrayList<>();

        JSONArray receipts = object.optJSONArray("content");
        if (receipts != null) {
            for (int i = 0; i < receipts.length(); i++) {
                Receipt next = new Receipt(receipts.getJSONObject(i));
                items.add(next);
            }
        }

        return items;
    }

    public long id;

    public Store store;

    private long user_id;

    public BigDecimal subpurchase_total;

    public BigDecimal tax_total;

    public BigDecimal purchase_total;

    public int total_items;

    public int trace_no;

    public CreditAccount credit_account;

    private Date create_date;

    private Date update_date;

    public Date purchase_date;

    public Set<ReceiptItem> receiptItems = new HashSet<>(0);

    public String getFormattedPurchasePrice() {

        return NumberFormat.getCurrencyInstance().format(this.purchase_total);

    }

    public Receipt(JSONObject object) {
        this.id = Long.parseLong(object.optString("id"));
        this.purchase_total = new BigDecimal(object.optString("purchase_total"));

        //2016-10-03T17:10:38.000+0000
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            Date date = formatter.parse(object.optString("purchase_date"));
        } catch (ParseException e) {
            //e.printStackTrace()
            Log.e(TAG, e.getMessage());
            this.purchase_date = new Date();
        }
        try {
            JSONObject json_store = object.getJSONObject("store");
            this.store = new Store();
            this.store.setAddress(json_store.optString("address"));
            this.store.setCity(json_store.optString("city"));
            this.store.setState(json_store.optString("state"));
            this.store.setZip(json_store.optString("zip"));
        } catch (Exception ex) {
            Log.e(TAG, "Unable to parse store from json");
        }
    }


    /*private  String encodeRawData(String raw) {
        return Base64.encodeToString(raw.getBytes(), Base64.NO_WRAP);
    }

    private  String decodeData() {
        return new String(Base64.decode(this.data, Base64.NO_WRAP));
    }

    public  String getData() {
        return TextUtils.isEmpty(this.data) ?
                this.data : decodeData();
    }*/

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        /*if (!TextUtils.isEmpty(this.name)) {
            object.put("attachmentName", this.name);
        }
        object.put("namespacedType", this.namespacedType);
        object.put("data", this.data);
        */
        return object;
    }

    /*@Override
    public  String toString() {
        return getData();
    }*/
}
