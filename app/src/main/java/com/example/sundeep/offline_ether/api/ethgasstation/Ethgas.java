package com.example.sundeep.offline_ether.api.ethgasstation;

import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.BalanceFactory;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class Ethgas {

    private RestClient restClient;
    private HttpUrl ethGasApi = HttpUrl.parse("https://ethgasstation.info/json/ethgasAPI.json");

    public Ethgas(RestClient restClient) {
        this.restClient = restClient;
    }

    public Observable<Ethgas> getBalance(List<String> addresses){
        return Observable.fromCallable(() -> {
            Request balanceReq = new Request.Builder().url(ethGasApi).build();
            JSONObject jObject = restClient.executeGet(balanceReq, "Ethgas");
            return BalanceFactory.getBalances(jObject);
        });
    }

}
