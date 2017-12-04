package com.example.moyutest.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.moyutest.model.MoyuUser;
import com.example.moyutest.model.Weibo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class Utility {

    public static String handlejoinResponse(String response, String pw, String phone) {
        String token = "";
        if (!TextUtils.isEmpty(response)) {
            try {
                Gson gson = new Gson();
                JSONObject joinusereObject = new JSONObject(response);
                JSONObject jsonObject = joinusereObject.getJSONObject("obj");
                String tokenString = joinusereObject.getString("token");
                String Content = jsonObject.toString();
                MoyuUser user = gson.fromJson(Content, MoyuUser.class);
                token = gson.fromJson(tokenString, String.class);
                Log.d("Phone", "token =" + token);
                user.setPassword(pw);
                user.setMobile(phone);
                Log.d("Phone", pw + "+" + phone);
                user.save();
                return token;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Phone", "" + e);
            }
        }
        return token;
    }

    public static String handleregistResponse(String response) {
        String success = "";
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject registObject = new JSONObject(response);
                success = registObject.getString("success");
                return success;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static String handletokenResponse(String response, String pw, String phone) {
        String token = "";
        if (!TextUtils.isEmpty(response)) {
            try {
                Gson gson = new Gson();
                JSONObject joinusereObject = new JSONObject(response);
                JSONObject jsonObject = joinusereObject.getJSONObject("obj");
                String tokenString = joinusereObject.getString("token");
                String Content = jsonObject.toString();
                MoyuUser user = gson.fromJson(Content, MoyuUser.class);
                token = gson.fromJson(tokenString, String.class);
                Log.d("Phone", "token =" + token);
                user.setPassword(pw);
                user.setMobile(phone);
                Log.d("Phone", pw + "+" + phone);
                user.save();
                return token;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Phone", "" + e);
            }
        }
        return token;
    }

    public static boolean sendweibo(String response) {
        boolean content = false;
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject weiboObject = new JSONObject(response);
                String success = weiboObject.getString("success");
                if (success != null && !success.equals("") && success.equals("true")) {
                    content = true;
                }
                return content;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public static boolean weibo(String response) {
//        List<Weibo> weibos = null;
        boolean content = false;
        if (!TextUtils.isEmpty(response)) {
            try {
                Gson gson = new Gson();
                JSONObject weiboObject = new JSONObject(response);
                String success = weiboObject.getString("success");
                if (success != null && !success.equals("") && success.equals("true")) {
                    content = true;
                }
 /*               JSONObject jsonObject = weiboObject.getJSONObject("obj");
                weibos = gson.fromJson();
                Log.d("Phone", weibos + "");*/
                return content;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Phone", "" + e);
            }
        }
        return content;
    }
//
//
//    public static MoyuUser handleWeatherResponse(String response) {
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
//            String weatherContent = jsonArray.getJSONObject(0).toString();
//            return new Gson().fromJson(weatherContent, MoyuUser.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
