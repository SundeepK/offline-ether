package com.example.sundeep.offline_ether.api.validators;

import com.example.sundeep.offline_ether.api.ResponseValidator;

import org.json.JSONException;
import org.json.JSONObject;

public class EtherScanValidator implements ResponseValidator {

    @Override
    public boolean validate(JSONObject jObject) {
        try {
           int status = jObject.getInt("status");
            return status == 0;
        } catch (JSONException e) {
            return false;
        }
    }
}
