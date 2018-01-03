package com.example.moyutest;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class FollowActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayoutManager layoutManager;
    private EasyRecyclerView followrecyclerView;
    private RecyclerArrayAdapter<Person> followadapter;
    private List<Person> followList = new ArrayList<>();
    private int intPage = 1;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        back = (ImageView) findViewById(R.id.followtoolbar_back);
        followrecyclerView = (EasyRecyclerView) findViewById(R.id.followRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        followrecyclerView.setLayoutManager(layoutManager);
        followrecyclerView.setAdapterWithProgress(followadapter = new RecyclerArrayAdapter<Person>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new PersonViewHolder(parent);
            }
        });
        followadapter.setMore(R.layout.view_more, this);
        followadapter.setNoMore(R.layout.view_nomore);
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
        api.getfollow(id_token, page, 10, "desc", "create_time")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonJson>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PersonJson personJson) {
                        followList.clear();
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
                            followList.add(new Person(userid, avatar, name, gender, introduction, location, follow, follower, myfollow));
                        }
                        followadapter.addAll(followList);
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
        followadapter.clear();
        intPage = 1;
        initRefresh(intPage);
    }

    @Override
    public void onLoadMore() {
        intPage = intPage + 1;
        initRefresh(intPage);
    }
}
