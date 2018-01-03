package com.example.moyutest.util;

/**
 * Created by Administrator on 2017/11/29.
 */

import com.example.moyutest.gson.CommentJson;
import com.example.moyutest.gson.ContentJson;
import com.example.moyutest.gson.PersonJson;
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
    Observable<JsonObject> login(@Field("phone") String phone, @Field("password") String password);

    @POST("logout")
    Observable<JsonObject> logout(@Header("authorization") String id_token);

    @FormUrlEncoded
    @POST("user/registered")
    Call<JsonObject> regist(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("user/join")
    Call<JsonObject> password(@Field("phone") String phone, @Field("password") String password,
                              @Field("nickname") String nickname, @Field("code") String code);

    @FormUrlEncoded
    @POST("weibo/ugc")
    Call<JsonObject> sendweibo(@Field("text") String weiboContent, @Header("authorization") String id_token);

    @GET("weibo/follow")
    Observable<ContentJson> weibo(@Header("authorization") String id_token, @Query("page") int page, @Query("size") int sizes, @Query("order") String order, @Query("column") String column);


    @FormUrlEncoded
    @POST("comment/ugc")
    Observable<JsonObject> sendcomment(@Field("wid") String weiboId, @Field("text") String weiboComment, @Header("authorization") String id_token);

    @GET("comment/list")
    Observable<CommentJson> comment(@Header("authorization") String id_token, @Query("wid") String weiboId, @Query("page") int page, @Query("size") int sizes, @Query("order") String order, @Query("column") String column);

    @Multipart
    @POST("user/upload")
    Observable<JsonObject> upload(@Part MultipartBody.Part file, @Header("authorization") String id_token);

    @FormUrlEncoded
    @POST("user/edit")
    Observable<JsonObject> edit(@Header("authorization") String id_token, @Field("nickname") String nickname, @Field("gender") String gender, @Field("introduction") String introduce, @Field("location") String location);

    @FormUrlEncoded
    @POST("weibo/like")
    Observable<JsonObject> like(@Field("wid") String wid, @Header("authorization") String id_token);

    @FormUrlEncoded
    @POST("weibo/dislike")
    Observable<JsonObject> unlike(@Field("wid") String wid, @Header("authorization") String id_token);

    @FormUrlEncoded
    @POST("query")
    Observable<PersonJson> query(@Header("authorization") String id_token, @Field("nickname") String nickname, @Field("page") int page, @Field("size") int sizes, @Field("order") String order, @Field("column") String column);

    @FormUrlEncoded
    @POST("user/follow")
    Observable<PersonJson> follow(@Header("authorization") String id_token, @Field("uid") int uid);

    @FormUrlEncoded
    @POST("user/unfollow")
    Observable<PersonJson> unfollow(@Header("authorization") String id_token, @Field("uid") int uid);

    @GET("user/follows")
    Observable<PersonJson> getfollow(@Header("authorization") String id_token, @Query("page") int page, @Query("size") int sizes, @Query("order") String order, @Query("column") String column);

    @GET("user/followers")
    Observable<PersonJson> getfollower(@Header("authorization") String id_token, @Query("page") int page, @Query("size") int sizes, @Query("order") String order, @Query("column") String column);

    @GET("user/profile")
    Observable<JsonObject> profile(@Header("authorization") String id_token);

    @GET("user/look")
    Observable<JsonObject> look(@Header("authorization") String id_token, @Query("uid") int uid);


    @GET("weibo/look")
    Observable<ContentJson> weibolook(@Header("authorization") String id_token, @Query("uid") int uid, @Query("page") int page, @Query("size") int sizes, @Query("order") String order, @Query("column") String column);
}
