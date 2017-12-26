package com.example.moyutest.util;

/**
 * Created by Administrator on 2017/11/29.
 */

import com.example.moyutest.gson.CommentJson;
import com.example.moyutest.gson.ContentJson;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/15.
 */

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Observable<JsonObject> login(@Field("mobile") String mobile, @Field("password") String password);

    @POST("logout")
    Observable<JsonObject> logout(@Header("authorization") String id_token);

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
    Observable<ContentJson> weibo(@Header("authorization") String id_token, @Query("from") String froms, @Query("size") String sizes);


    @FormUrlEncoded
    @POST("comment/ugc")
    Observable<JsonObject> sendcomment(@Field("wid") String weiboId, @Field("text") String weiboComment, @Header("authorization") String id_token);

    @GET("comment/list")
    Observable<CommentJson> comment(@Header("authorization") String id_token, @Query("wid") String weiboId, @Query("from") String froms, @Query("size") String sizes);

    @Multipart
    @POST("user/upload")
    Observable<JsonObject> upload(@Part MultipartBody.Part file, @Header("authorization") String id_token);



}