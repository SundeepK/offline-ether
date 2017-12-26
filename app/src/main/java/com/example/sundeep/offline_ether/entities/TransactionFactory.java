package com.example.sundeep.offline_ether.entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TransactionFactory {

    private final static String TAG = "TransactionFactory";
    private static final String RESULT = "result";
    private static final String BLOCK_HASH = "blockHash";
    private static final String BLOCK_NUMBER = "blockNumber";
    private static final String CONFIRMATIONS = "confirmations";
    private static final String CUMULATIVE_GAS_USED = "cumulativeGasUsed";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String TIME_STAMP = "timeStamp";
    private static final String GAS_PRICE = "gasPrice";
    private static final String GAS = "gas";
    private static final String VALUE = "value";
    private static final String NONCE = "nonce";
    private static final String IS_ERROR = "isError";
    private static final String HASH = "hash";

    public static List<EtherTransaction> getTransactions(JSONObject jObject)  {
        List<EtherTransaction> transactions = new ArrayList<>();
        JSONArray jArray;
        try {
            jArray = jObject.getJSONArray(RESULT);
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse response, no results found.", e);
            return transactions;
        }
        for (int i=0; i < jArray.length(); i++) {
            try {
                JSONObject transaction = jArray.getJSONObject(i);
                EtherTransaction etherTransaction = EtherTransaction.newBuilder()
                        .setBlockHash(transaction.getString(BLOCK_HASH))
                        .setBlockNumber(transaction.getString(BLOCK_NUMBER))
                        .setConfirmations(transaction.getInt(CONFIRMATIONS))
                        .setCumulativeGasUsed(transaction.getString(CUMULATIVE_GAS_USED))
                        .setFrom(transaction.getString(FROM))
                        .setTo(transaction.getString(TO))
                        .setTimeStamp(transaction.getLong(TIME_STAMP))
                        .setGas(transaction.getString(GAS_PRICE))
                        .setGas(transaction.getString(GAS))
                        .setValue(transaction.getString(VALUE))
                        .setNonce(transaction.getInt(NONCE))
                        .setIsError(transaction.getInt(IS_ERROR))
                        .setHash(transaction.getString(HASH))
                        .build();
                transactions.add(etherTransaction);
            } catch (JSONException | NumberFormatException e) {
                Log.e(TAG, "Unable to parse transaction.", e);
            }
        }
        return transactions;
    }



}
