package com.yrbase.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yrbase.utils.ViewUtil;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by kico 2018/5/18on weiniu.
 */

public class HashMapUtil extends HashMap<String, Object> implements Serializable {


    private Gson gson;


    public Gson getGson() {
        return gson == null ? new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() : gson;
    }

   /* public HashMapUtil getHashMap() {
        //putParams(MOBILE, MyApplication.getApplication().mobile);
        //putParams(MOBILE, "13027711679");//13027711679
        // putParams(TOKEN, MyApplication.getApplication().token);
        //putParams(TOKEN, "22a734bf740db30b20107ca235d60238");
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return this;
    }
*/

    public HashMapUtil putParams(String key, String value) {
        if (!ViewUtil.isEmpty(key) && !ViewUtil.isEmpty(value)) {
            put(key, value);
        }
        return this;
    }


    public HashMapUtil putParams(String key, int value) {
        if (!ViewUtil.isEmpty(key)) {
            put(key, value + "");
        }
        return this;
    }


    public HashMapUtil putParams(String key, long value) {
        if (!ViewUtil.isEmpty(key)) {
            put(key, value + "");
        }
        return this;
    }


    public HashMapUtil putParams(String key, Object value) {
        if (!ViewUtil.isEmpty(key) && null != value) {
            put(key, value);
        }
        return this;
    }
}
