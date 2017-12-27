package com.example.sundeep.offline_ether.entities;

import com.example.sundeep.offline_ether.api.JsonConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class EthGasFactory implements JsonConverter<EthGas> {

    private final static String FASTEST_WAIT = "fastestWait";
    private final static String FAST = "fast";
    private final static String BLOCK_TIME = "block_time";
    private final static String AVG = "average";
    private final static String SAFE_LOW_WAIT = "safeLowWait";
    private final static String FASTEST = "fastest";
    private final static String AVG_WAIT = "avgWait";
    private final static String BLOCK_NUM = "blockNum";
    private final static String SPEED = "speed";
    private final static String FAST_WAIT = "fastWait";
    private final static String SAFE_LOW = "safeLow";

    @Override
    public EthGas convert(JSONObject jObject) throws JSONException {
        return EthGas.newBuilder()
                .setAverage(jObject.getInt(AVG))
                .setAvgWait(jObject.getInt(AVG_WAIT))
                .setBlock_time(jObject.getInt(BLOCK_TIME))
                .setBlockNum(jObject.getInt(BLOCK_NUM))
                .setFast(jObject.getInt(FAST))
                .setFastest(jObject.getInt(FASTEST))
                .setSpeed(jObject.getInt(SPEED))
                .setSafeLow(jObject.getInt(SAFE_LOW))
                .setSafeLowWait(jObject.getInt(SAFE_LOW_WAIT))
                .setFastestWait(jObject.getInt(FAST_WAIT))
                .setFastestWait(jObject.getInt(FASTEST_WAIT))
                .build();
    }

}
