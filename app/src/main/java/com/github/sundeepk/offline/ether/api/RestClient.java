package com.github.sundeepk.offline.ether.api;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RestClient {

    private final static String TAG = "RestClient";
    private OkHttpClient okHttpClient;

    public RestClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public <T> T executeGet(Request transactionReq, JsonAdapter<T> jsonAdapter) throws JSONException, IOException {
        String url = transactionReq.url().toString();
        Log.d(TAG, "Executing " + transactionReq.url().toString());
        Response response = okHttpClient.newCall(transactionReq).execute();
        ResponseBody body = response.body();
        if (!response.isSuccessful() || body == null) {
            throw new IOException("Unable to fetch " + url);
        }
        String json = getString(body, url);
        return jsonAdapter.fromJson(json);
    }

    private String getString(ResponseBody body, String url) throws IOException {
        String responseBody = body.string();
        Log.d(TAG, "Found " + responseBody + " for url " + url);
        return responseBody;
    }


}
