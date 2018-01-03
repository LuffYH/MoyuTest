package com.example.moyutest.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.moyutest.db.MoyuUser;
import com.example.moyutest.model.Comments;
import com.example.moyutest.model.Person;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class HandleResponse {

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

    public static List<Person> handlelook(String response) {
        List<Person> personList = new ArrayList<>();
        try {
            JSONObject lookObject = new JSONObject(response);
            JSONObject jsonObject = lookObject.getJSONObject("obj");
            String name = jsonObject.getString("nickname");
            int userid = jsonObject.getInt("userId");
            String avatar = jsonObject.getString("avatar");
            int follow = jsonObject.getInt("follow");
            int follower = jsonObject.getInt("follower");
            int gender = jsonObject.getInt("gender");
            String introduction = jsonObject.getString("introduction");
            String location = jsonObject.getString("location");
            boolean myfollow = jsonObject.getBoolean("myFollow");
            Log.d("Phone", location);
            personList.add(new Person(userid, avatar, name, gender, introduction, location, follow, follower, myfollow));
            return personList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return personList;
    }


    public static void handleProfile(String response) {
        Gson gson = new Gson();
        try {
            JSONObject ProfileObject = new JSONObject(response);
            JSONObject contentObject = ProfileObject.getJSONObject("obj");
            String profileContent = contentObject.toString();
            MoyuUser user = gson.fromJson(profileContent, MoyuUser.class);
            String id = SharedPreferencesUtil.getIdFromDB();
            user.updateAll("userid = ?", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String handleNewComment(String response) {
        String newcomment = "";
        try {
            JSONObject newCommentObj = new JSONObject(response);
            newcomment = newCommentObj.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newcomment;
    }

    public static String handleregistResponse(String response) {
        String obj = "";
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject registObject = new JSONObject(response);
                obj = registObject.getString("obj");
                return obj;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static String handlerupload(String response) {
        String obj = "";
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject upload = new JSONObject(response);
                obj = upload.getString("obj");
                return obj;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
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
                Log.d("Phone", "登录token =" + token);
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

    public static boolean sendcomment(String response) {
        boolean comment = false;
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject commentObject = new JSONObject(response);
                String success = commentObject.getString("success");
                if (success != null && !success.equals("") && success.equals("true")) {
                    comment = true;
                }
                return comment;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return comment;
    }

    public static boolean weibo(String response) {
//        List<ContentJson> weibos = null;
        boolean content = false;
        if (!TextUtils.isEmpty(response)) {
            try {
                Gson gson = new Gson();
                JSONObject weiboObject = new JSONObject(response);
                String success = weiboObject.getString("success");
                if (success != null && !success.equals("") && success.equals("true")) {
                    content = true;
                }
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
