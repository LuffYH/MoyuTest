package com.example.moyutest.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.moyutest.ContentActivity;
import com.example.moyutest.db.MoyuUser;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SharedPreferencesUtil {
    public static String getIdTokenFromXml(Context mcontext) {
        SharedPreferences pref = mcontext.getSharedPreferences("data", mcontext.MODE_PRIVATE);
        MoyuUser moyuUser = DataSupport.findLast(MoyuUser.class);
        Long id = moyuUser.getUserId();
        String token = pref.getString("token", "");
        String id_token = id + "_" + token;
        Log.d("Phone", "id_token =" + id_token);
        return id_token;
    }

    public static void putTokenFromXml(Context mcontext, String token) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("data", mcontext.MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static void cleanToken(Context mcontext) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("data", mcontext.MODE_PRIVATE).edit();
        editor.clear().commit();

    }

    public static String getIdFromDB() {
        MoyuUser moyuUser = DataSupport.findLast(MoyuUser.class);
        Long id = moyuUser.getUserId();
        return String.valueOf(id);
    }
}
