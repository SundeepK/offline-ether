package com.github.sundeepk.offline.ether.api;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonConverter<T> {

    public T convert(JSONObject jObject) throws JSONException;

}
