package com.example.sundeep.offline_ether.api.etherscan;

import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.entities.EtherTransactionResultsJson;
import com.example.sundeep.offline_ether.entities.Nonce;
import com.google.common.base.Joiner;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class EtherScan {

    private final static String TAG = "EtherScan";

    private RestClient restClient;
    private String apiEndpoint;
    private Moshi moshi;

    public EtherScan(RestClient restClient, String apiEndpoint) {
        this.restClient = restClient;
        this.apiEndpoint = apiEndpoint;
        this.moshi = new Moshi.Builder().add(new EtherTransaction.EtherTransactionAdapter()).build();
    }

    public Observable<List<Balance>> getBalance(List<String> addresses){
        return Observable.fromCallable(() -> {
            Request balanceReq = getBalanceRequest(addresses);
            Type type = Types.newParameterizedType(List.class, Balance.class);
            JsonAdapter<List<Balance>> adapter = moshi.adapter(type);
            return restClient.executeGet(balanceReq, adapter);
        });
    }

    public Observable<List<EtherTransaction>> getTransactions(String address, int page){
        return Observable.fromCallable(() -> {
            Request transactionReq = getTransactionRequest(address, page);
            JsonAdapter<EtherTransactionResultsJson> jsonAdapter = moshi.adapter(EtherTransactionResultsJson.class);
            EtherTransactionResultsJson o = restClient.executeGet(transactionReq, jsonAdapter);
            return o.getResults();
        });
    }

    public Observable<Nonce> getNonce(String address){
        return Observable.fromCallable(() -> {
            Request transactionReq = getNonceRequest(address);
            JsonAdapter<Nonce> jsonAdapter = moshi.adapter(Nonce.class);
            return restClient.executeGet(transactionReq, jsonAdapter);
        });
    }

    private Request getBalanceRequest(List<String> addresses) {
        String addressesCommaSeparated = Joiner.on(",").join(addresses);
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(apiEndpoint)
                .addPathSegment("api")
                .addQueryParameter("module", "account")
                .addQueryParameter("action", "balancemulti")
                .addQueryParameter("address", addressesCommaSeparated)
                .addQueryParameter("tag", "latest")
                .build();
        return new Request.Builder()
                .url(url)
                .build();
    }

    private Request getNonceRequest(String address) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(apiEndpoint)
                .addPathSegment("api")
                .addQueryParameter("module", "proxy")
                .addQueryParameter("action", "eth_getTransactionCount")
                .addQueryParameter("address", address)
                .build();
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
        return new Request.Builder()
                .url(url)
                .build();
    }

}
