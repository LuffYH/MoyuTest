package com.example.moyutest;

import android.content.Intent;
import android.media.Image;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moyutest.adapter.ContentViewHolder;
import com.example.moyutest.gson.ContentJson;
import com.example.moyutest.gson.PersonJson;
import com.example.moyutest.model.Contents;
import com.example.moyutest.model.Person;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.HandleResponse;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String USER_ID = "user_Id";
    public static final String AVATAR = "Avatar";
    public static final String NICK_NAME = "nick_name";
    public static final String GENDER = "gender";
    public static final String LOCATION = "location";
    public static final String INTRODUCTION = "introduction";
    public static final String MY_FOLLOW = "my_follow";
    public static final String FOLLOWER = "follower";
    public static final String FOLLOW = "follow";
    private TextView txtname, txtfollow, txtfollower, txtlocation, txtintroduction;
    private ImageView imgavatar, imggender, back;
    private String name, avatar, location, introduction;
    private int follow, follower, gender, userid;
    private boolean myfollow;
    private LinearLayoutManager layoutManager;
    private EasyRecyclerView usercontentrecyclerView;
    private RecyclerArrayAdapter<Contents> usercontentadapter;
    private List<Contents> usercontentList = new ArrayList<>();
    private int intPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findById();
        Intent intent = getIntent();
        getData(intent);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutManager = new LinearLayoutManager(this);
        usercontentrecyclerView.setLayoutManager(layoutManager);

        usercontentrecyclerView.setAdapterWithProgress(usercontentadapter = new RecyclerArrayAdapter<Contents>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ContentViewHolder(parent);
            }
        });
        usercontentadapter.setMore(R.layout.view_more, this);
        usercontentadapter.setNoMore(R.layout.view_nomore);
        usercontentrecyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void getData(Intent intent) {
        userid = intent.getIntExtra(USER_ID, 0);
        Api api = RetrofitProvider.create().create(Api.class);
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
        api.look(id_token, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        String responseText = jsonObject.toString();
                        List<Person> personList = HandleResponse.handlelook(responseText);
                        name = personList.get(0).getNickname();
                        avatar = personList.get(0).getAvatar();
                        gender = personList.get(0).getGender();
                        location = personList.get(0).getLocation();
                        introduction = personList.get(0).getIntroduction();
                        userid = personList.get(0).getUesrId();
                        follow = personList.get(0).getFollow();
                        follower = personList.get(0).getFollower();
                        myfollow = personList.get(0).isMyFollow();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Phone", "" + e);

                    }

                    @Override
                    public void onComplete() {
                        setText();
                    }
                });
    }

    private void setText() {
        txtname.setText(name);
        txtfollow.setText("" + follow);
        txtfollower.setText("" + follower);
        Log.d("Phone", location);

        if (location.equals("null")) {
            txtlocation.setText("暂无地址信息");
        } else {
            txtlocation.setText(location);
        }

        if (introduction.equals("null")) {
            txtintroduction.setText("暂无个人简介");
        } else {
            txtintroduction.setText("个人简介: " + introduction);
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.account);
        Glide.with(ProfileActivity.this)
                .load("http://120.79.42.49:8080/images/avatar/" + avatar)
                .apply(requestOptions)
                .into(imgavatar);
        if (gender == 0) {
            imggender.setImageResource(R.drawable.man);
        } else {
            imggender.setImageResource(R.drawable.women);
        }
    }

    private void findById() {
        usercontentrecyclerView = (EasyRecyclerView) findViewById(R.id.userrecycler_view);
        back = (ImageView) findViewById(R.id.usertoolbar_back);
        txtname = (TextView) findViewById(R.id.user_name);
        txtfollow = (TextView) findViewById(R.id.user_follow);
        txtfollower = (TextView) findViewById(R.id.user_follower);
        txtlocation = (TextView) findViewById(R.id.user_location);
        txtintroduction = (TextView) findViewById(R.id.user_introduction);
        imgavatar = (ImageView) findViewById(R.id.user_img);
        imggender = (ImageView) findViewById(R.id.user_gender);
    }

    private void initRefresh(int page) {
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
        Api api = RetrofitProvider.create().create(Api.class);
        api.weibolook(id_token, userid, page, 6, "desc", "create_time")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentJson>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContentJson personJson) {
                        usercontentList.clear();
                        List<ContentJson.ObjBean> WeiboBean = personJson.getObj();
                        for (int z = 0; z < WeiboBean.size(); z++) {
                            String mauthorName = WeiboBean.get(z).getAuthorName();
                            String mcontent = WeiboBean.get(z).getContent();
                            int mimageAmount = WeiboBean.get(z).getImageAmount();
                            int mweiboLike = WeiboBean.get(z).getLikeAmount();
                            String mcreateTime = WeiboBean.get(z).getCreateTime();
                            int mcommentAmount = WeiboBean.get(z).getCommentAmount();
                            int mweiboId = WeiboBean.get(z).getMicroBlogId();
                            String mauthorAvatar = WeiboBean.get(z).getAuthorAvatar();
                            int mauthorId = WeiboBean.get(z).getAuthorId();
                            boolean mmylike = WeiboBean.get(z).isMyLike();
                            usercontentList.add(new Contents(mweiboId, mauthorId, mauthorName, mauthorAvatar, mcontent, mimageAmount, mcommentAmount, mweiboLike, mcreateTime, mmylike));
                        }
                        usercontentadapter.addAll(usercontentList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onRefresh() {
        usercontentadapter.clear();
        intPage = 1;
        initRefresh(intPage);
    }

    @Override
    public void onLoadMore() {
        intPage = intPage + 1;
        initRefresh(intPage);
    }
}
