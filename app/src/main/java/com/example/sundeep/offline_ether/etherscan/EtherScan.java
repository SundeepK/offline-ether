package com.example.sundeep.offline_ether.etherscan;

import android.util.Log;

import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.BalanceFactory;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.entities.TransactionFactory;
import com.google.common.base.Joiner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
            JSONObject jObject = executeGet(balanceReq, "balance");
            return BalanceFactory.getBalances(jObject);
        });
    }

    public Observable<List<EtherTransaction>> getTransactions(String address, int page){
        return Observable.fromCallable(() -> {
            Request transactionReq = getTransactionRequest(address, page);
            JSONObject jObject = executeGet(transactionReq, "transactions");
            return TransactionFactory.getTransactions(jObject);
        });
    }

    private JSONObject executeGet(Request transactionReq, String type) throws IOException, EtherScanException, JSONException {
        Response response = okHttpClient.newCall(transactionReq).execute();
        ResponseBody body = response.body();
        if (!response.isSuccessful() || body == null) {
            throw new EtherScanException("Unable to fetch " + type);
        }
        String responseBody = body.string();
        Log.d(TAG, "Found " + type + " " + responseBody);
        JSONObject jObject = new JSONObject(responseBody);
        int status = jObject.getInt("status");
        if (status == 0) {
            throw new EtherScanException("Unable to fetch " + type);
        }
        return jObject;
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

    private Request getTransactionRequest(String address, int page) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(apiEndpoint)
                .addPathSegment("api")
                .addQueryParameter("module", "account")
                .addQueryParameter("action", "txlist")
                .addQueryParameter("address", address)
                .addQueryParameter("startblock", "0")
                .addQueryParameter("endblock", "99999999")
                .addQueryParameter("sort", "desc")
                .addQueryParameter("offset", "50")
                .addQueryParameter("page", ""+ page)
                .build();
        Log.d(TAG, "transaction url " + url.toString());
        return new Request.Builder()
                .url(url)
                .build();
    }

}
