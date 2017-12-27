package com.example.sundeep.offline_ether.entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

public class NonceFactory {

    private final static String TAG = "NonceFactory";
    private static final String RESULT = "result";

    public static Nonce getNonce(JSONObject jObject)  {
        String nonce = "-1";
        try {
            nonce = jObject.getString(RESULT);
            return new Nonce(new BigInteger(nonce, 16));
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse transaction.", e);
        }
        return new Nonce(new BigInteger("-1"));
    }



}
