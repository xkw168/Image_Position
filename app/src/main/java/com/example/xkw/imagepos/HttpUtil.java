package com.example.xkw.imagepos;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xkw on 2018/4/29.
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static final String mapUriStr =
            "https://maps.googleapis.com/maps/api/geocode/json?latlng={0},{1}&language=zh-CN&key=AIzaSyCRWRqJu0iWGt6Kmnt6d1BooK9JVHwD9XM";

    public static void sendRequestGetAsy(float[] latLong, okhttp3.Callback callback){
        String uriStr = MessageFormat.format(mapUriStr, latLong[0], latLong[1]);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(uriStr)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static String sendRequestGetSyn(String address){
        String str = "";
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(address)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                str = response.body().string();
            }else {
                str = "error";
            }
        }catch (Exception e){
            Log.d(TAG, "sendRequestGetSyn: " + e.toString());
        }
        return str;
    }

    public static MapBean parseResult(String jsonData){
        Gson gson = new Gson();
        return gson.fromJson(jsonData, MapBean.class);
    }
}
