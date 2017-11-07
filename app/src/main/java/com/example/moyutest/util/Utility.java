package com.example.moyutest.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.moyutest.model.MoyuUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/22.
 */

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */

    public static boolean handlejoinResponse(String response, String pw, String phone) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject joinusereObject = new JSONObject(response);
                MoyuUser user = new MoyuUser();
                user.setUserId(joinusereObject.getLong("userId"));
                user.setPassword(pw);
                user.setMobile(phone);
                Log.d("Phone", pw + "+" + phone);
                user.setEmail(joinusereObject.getString("email"));
                user.setAvatar(joinusereObject.getString("avatar"));
                user.setNickname(joinusereObject.getString("nickname"));
                user.setGender(joinusereObject.getInt("gender"));
                user.setIntroduce(joinusereObject.getString("introduce"));
                user.setLocation(joinusereObject.getString("location"));
                user.setFollow(joinusereObject.getLong("follow"));
                user.setFollower(joinusereObject.getLong("follower"));
                user.save();
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Phone", "" + e);
            }
        }
        return false;
    }

    public static String handletokenResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject joinusereObject = new JSONObject(response);
                String token = joinusereObject.getString("token");
                return token;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Phone", "" + e);
            }
        }
        return "";
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
