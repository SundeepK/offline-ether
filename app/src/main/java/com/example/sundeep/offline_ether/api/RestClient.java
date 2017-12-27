package com.example.sundeep.offline_ether.api;

import android.util.Log;

import com.example.sundeep.offline_ether.api.exception.EtherApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RestClient {

    private final static String TAG = "RestClient";

    private OkHttpClient okHttpClient;
    private ResponseValidator responseValidator;

    public RestClient(OkHttpClient okHttpClient, ResponseValidator responseValidator) {
        this.okHttpClient = okHttpClient;
        this.responseValidator = responseValidator;
    }

    public <T> T executeGet(Request transactionReq, JsonConverter<T> jsonConverter) throws EtherApiException, JSONException {
        String url = transactionReq.url().toString();
        Log.d(TAG, "Executing " + transactionReq.url().toString());
        Response response = getResponse(transactionReq, url);
        ResponseBody body = response.body();
        if (!response.isSuccessful() || body == null) {
            throw new EtherApiException("Unable to fetch " + url);
        }
        JSONObject jObject = getString(body, url);
        if(!responseValidator.validate(jObject)){
            throw new EtherApiException("Unable to validate response " + jObject);
        }
        return jsonConverter.convert(jObject);
    }

    private JSONObject getString(ResponseBody body, String url) throws EtherApiException, JSONException {
        try {
            String responseBody = body.string();
            Log.d(TAG, "Found " + responseBody + " for url " + url);
            return new JSONObject(responseBody);
        } catch (IOException e) {
            throw new EtherApiException("Error when reading body for api call " + url, e);
        }
    }

    private Response getResponse(Request transactionReq, String url) throws EtherApiException {
        try {
            return okHttpClient.newCall(transactionReq).execute();
        } catch (IOException e) {
            throw new EtherApiException("Error when executing api call " + url, e);
        }
    }

}
