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
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class EtherApi {

    private final static String TAG = "EtherApiScan";
    private HttpUrl ethGasApi = HttpUrl.parse("https://ethgasstation.info/json/ethgasAPI.json");
    private HttpUrl price;
    private RestClient restClient;
    private String apiEndpoint;
    private Moshi moshi;

    public EtherApi(RestClient restClient, String apiEndpoint) {
        this.restClient = restClient;
        this.apiEndpoint = apiEndpoint;
        this.moshi = new Moshi.Builder().add(new EtherTransaction.EtherTransactionAdapter()).build();
        Currency currency = Currency.getInstance(Locale.getDefault());
        String currencyCode = currency.getCurrencyCode();
        this.price = HttpUrl.parse("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=BTC,USD,EUR,GBP," + currencyCode);
    }

    public Observable<Balances> getBalance(Collection<String> addresses){
        if (addresses.isEmpty()) {
            return Observable.empty();
        }
        return Observable.fromCallable(() -> {
            Request balanceReq = getBalanceRequest(addresses);
            Log.d(TAG, "balanceReq " + balanceReq.url().toString());
            JsonAdapter<Balances> jsonAdapter = moshi.adapter(Balances.class);
            return restClient.executeGet(balanceReq, jsonAdapter);
        });
    }

    public Observable<Map<String, String>> getPrices(){
        return Observable.fromCallable(() -> {
            Request req = new Request.Builder().url(price).build();
            Type type = Types.newParameterizedType(Map.class, String.class, String.class);
            JsonAdapter<Map<String,String>> adapter = moshi.adapter(type);
            return restClient.executeGet(req, adapter);
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
