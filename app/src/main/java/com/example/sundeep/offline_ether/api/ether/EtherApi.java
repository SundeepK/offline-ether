package com.example.sundeep.offline_ether.api.ether;

import android.util.Log;

import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.entities.Balances;
import com.example.sundeep.offline_ether.entities.EthGas;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.entities.EtherTransactionResultsJson;
import com.example.sundeep.offline_ether.entities.Nonce;
import com.example.sundeep.offline_ether.entities.SentTransaction;
import com.google.common.base.Joiner;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class EtherApi {

    private final static String TAG = "EtherApiScan";
    private HttpUrl ethGasApi = HttpUrl.parse("https://ethgasstation.info/json/ethgasAPI.json");
    private RestClient restClient;
    private String apiEndpoint;
    private Moshi moshi;

    public EtherApi(RestClient restClient, String apiEndpoint) {
        this.restClient = restClient;
        this.apiEndpoint = apiEndpoint;
        this.moshi = new Moshi.Builder().add(new EtherTransaction.EtherTransactionAdapter()).build();
    }

    public Observable<Balances> getBalance(Collection<String> addresses){
        return Observable.fromCallable(() -> {
            Request balanceReq = getBalanceRequest(addresses);
            Log.d(TAG, "balanceReq " + balanceReq.url().toString());
            JsonAdapter<Balances> jsonAdapter = moshi.adapter(Balances.class);
            return restClient.executeGet(balanceReq, jsonAdapter);
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

    public Observable<EthGas> getEthgas(){
        return Observable.fromCallable(() -> {
            Request req = new Request.Builder().url(ethGasApi).build();
            JsonAdapter<EthGas> jsonAdapter = moshi.adapter(EthGas.class);
            return restClient.executeGet(req, jsonAdapter);
        });
    }

    public Observable<SentTransaction> sendTransaction(String signedTransaction){
        return Observable.fromCallable(() -> {
            Request req = getSignedTransaction(signedTransaction);
            JsonAdapter<SentTransaction> jsonAdapter = moshi.adapter(SentTransaction.class);
            return restClient.executeGet(req, jsonAdapter);
        });
    }

    private Request getBalanceRequest(Collection<String> addresses) {
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

    private Request getSignedTransaction(String signed) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(apiEndpoint)
                .addPathSegment("api")
                .addQueryParameter("module", "proxy")
                .addQueryParameter("action", "eth_sendRawTransaction")
                .addQueryParameter("hex", signed)
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
