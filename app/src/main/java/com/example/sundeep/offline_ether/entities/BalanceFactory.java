package com.example.sundeep.offline_ether.entities;

import android.util.Log;

import com.example.sundeep.offline_ether.api.JsonConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BalanceFactory implements JsonConverter<List<Balance>> {

    private final static String TAG = "BalanceFactory";
    private static final String ACCOUNT = "account";
    private static final String BALANCE = "balance";
    private static final String RESULT = "result";

    @Override
    public List<Balance> convert(JSONObject jObject) throws JSONException {
        List<Balance> balances = new ArrayList<>();
        JSONArray jArray = jObject.getJSONArray(RESULT);
        for (int i=0; i < jArray.length(); i++) {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                String account = oneObject.getString(ACCOUNT);
                BigInteger balance = new BigInteger(oneObject.getString(BALANCE));
                balances.add(new Balance(account, balance));
            } catch (JSONException | NumberFormatException e) {
                Log.e(TAG, "Unable to parse balance.", e);
            }
        }
        return balances;
    }
}
