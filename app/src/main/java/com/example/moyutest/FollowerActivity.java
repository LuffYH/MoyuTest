package com.example.moyutest;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moyutest.adapter.FollowerViewHolder;
import com.example.moyutest.adapter.PersonViewHolder;
import com.example.moyutest.gson.PersonJson;
import com.example.moyutest.model.Person;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FollowerActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayoutManager layoutManager;
    private EasyRecyclerView followerrecyclerView;
    private RecyclerArrayAdapter<Person> followeradapter;
    private List<Person> followerList = new ArrayList<>();
    private int intPage = 1;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        back = (ImageView) findViewById(R.id.followertoolbar_back);
        followerrecyclerView = (EasyRecyclerView) findViewById(R.id.followerRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        followerrecyclerView.setLayoutManager(layoutManager);
        followerrecyclerView.setAdapterWithProgress(followeradapter = new RecyclerArrayAdapter<Person>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new FollowerViewHolder(parent);
            }
        });
        followeradapter.setMore(R.layout.view_more, this);
        followeradapter.setNoMore(R.layout.view_nomore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        onRefresh();
    }

    private void initRefresh(int page) {
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
        Api api = RetrofitProvider.create().create(Api.class);
        api.getfollower(id_token, page, 10, "desc", "create_time")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonJson>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PersonJson personJson) {
                        followerList.clear();
                        List<PersonJson.ObjBean> personBean = personJson.getObj();
                        for (int z = 0; z < personBean.size(); z++) {
                            String name = personBean.get(z).getNickname();
                            String avatar = personBean.get(z).getAvatar();
                            int gender = personBean.get(z).getGender();
                            String location = personBean.get(z).getLocation();
                            String introduction = personBean.get(z).getIntroduction();
                            int userid = personBean.get(z).getUserId();
                            int follow = personBean.get(z).getFollow();
                            int follower = personBean.get(z).getFollower();
                            boolean myfollow = personBean.get(z).isMyFollow();
                            followerList.add(new Person(userid, avatar, name, gender, introduction, location, follow, follower, myfollow));
                        }
                        followeradapter.addAll(followerList);
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
        followeradapter.clear();
        intPage = 1;
        initRefresh(intPage);
    }

    @Override
    public void onLoadMore() {
        intPage = intPage + 1;
        initRefresh(intPage);
    }
}
