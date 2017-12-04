package com.example.moyutest.util;

/**
 * Created by Administrator on 2017/11/29.
 */

import com.example.moyutest.model.MoyuUser;
import com.example.moyutest.model.Weibo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/11/15.
 */

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> login(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/registered")
    Call<JsonObject> regist(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("user/join")
    Call<JsonObject> password(@Field("mobile") String mobile, @Field("password") String password,
                              @Field("nickname") String nickname, @Field("code") String code);

    @FormUrlEncoded
    @POST("weibo/ugc")
    Call<JsonObject> sendweibo(@Field("text") String weiboContent, @Header("authorization") String id_token);

    @GET("weibo/follow")
    Observable<Weibo> weibo(@Header("authorization") String id_token, @Query("from") String froms, @Query("size") String sizes);

}