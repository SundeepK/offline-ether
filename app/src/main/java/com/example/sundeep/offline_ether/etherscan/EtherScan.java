package com.example.sundeep.offline_ether.etherscan;

import android.util.Log;

import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.BalanceFactory;
import com.google.common.base.Joiner;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class EtherScan {

    private final static String TAG = "EtherScan";

    private OkHttpClient okHttpClient;
    private String apiEndpoint;

    public EtherScan(OkHttpClient okHttpClient, String apiEndpoint) {
        this.okHttpClient = okHttpClient;
        this.apiEndpoint = apiEndpoint;
    }

    public Observable<List<Balance>> getBalance(List<String> addresses){
        return Observable.fromCallable(() -> {
            Request balanceReq = getBalanceRequest(addresses);
            ResponseBody body = okHttpClient.newCall(balanceReq).execute().body();
            if (body == null) {
                throw new EtherScanException("Unable to fetch balances.");
            }
            String responseBody = body.string();
            Log.d(TAG, "Found response " + responseBody);
            JSONObject jObject = new JSONObject(responseBody);
            int status = jObject.getInt("status");
            if (status == 0) {
                throw new EtherScanException("Unable to fetch balances.");
            }

            return BalanceFactory.getBalances(jObject);
        });
    }

    private Request getBalanceRequest(List<String> addresses) {
        String addressesCommaSeparated = Joiner.on(",").join(addresses);
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(apiEndpoint)
                .addPathSegment("api")
                .query("module=account&action=balancemulti&address=" + addressesCommaSeparated +"&tag=latest")
                .build();
        Log.d(TAG, "balance url " + url.toString());
        return new Request.Builder()
                .url(url)
                .build();
    }

}
