/*
package com.example.moyutest.util;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

*/
/**
 * Created by Administrator on 2017/8/22.
 *//*


public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void login(String address, String phone, String password, okhttp3.Callback callback) {
        OkHttpClient clientlogin = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("mobile", phone)
                .add("password", password)
                .build();
        Request request = new Request.Builder().url(address).post(body).build();
        clientlogin.newCall(request).enqueue(callback);
    }

    public static void postphone(String url, String phone, okhttp3.Callback callback) {
        OkHttpClient clientPhone = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("mobile", phone)
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        clientPhone.newCall(request).enqueue(callback);
    }

    public static void postjoin(String url, String phone, String password, String nickname, String code, okhttp3.Callback callback) {
        OkHttpClient clientjoin = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("mobile", phone)
                .add("password", password)
                .add("nickname", nickname)
                .add("code", code)
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        clientjoin.newCall(request).enqueue(callback);
    }
}

*/
