package com.example.sundeep.offline_ether.api.validators;

import com.example.sundeep.offline_ether.api.ResponseValidator;

public class EtherScanValidator implements ResponseValidator {
    @Override
    public boolean validate(Object object) {
        return false;
    }

//    @Override
//    public boolean validate(JSONObject jObject) {
//        try {
//           int status = jObject.getInt("status");
//            return status == 0;
//        } catch (JSONException e) {
//            return false;
//        }
//    }
}
