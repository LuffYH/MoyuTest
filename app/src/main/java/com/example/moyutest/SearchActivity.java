package com.example.moyutest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchActivity extends BaseActivity implements View.OnClickListener, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private Button btnsearch;
    private EditText etsearch;
    private EasyRecyclerView searchRV;
    private String strSearch;
    private LinearLayoutManager layoutManager;
    private List<Person> searchList = new ArrayList<>();
    private RecyclerArrayAdapter<Person> adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnsearch = (Button) findViewById(R.id.btnsearch);
        etsearch = (EditText) findViewById(R.id.txtsearch);
        searchRV = (EasyRecyclerView) findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        searchRV.setLayoutManager(layoutManager);
        searchRV.setAdapter(adapter = new RecyclerArrayAdapter<Person>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new PersonViewHolder(parent);
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        btnsearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsearch:
                strSearch = etsearch.getText().toString();
                page = 1;
                searchList.clear();
                adapter.clear();
                if (strSearch.equals("") || strSearch == "") {
                    return;
                }
                initRefresh(page);
                break;
            default:
                break;
        }
    }

    private void initRefresh(int page) {
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
        Api api = RetrofitProvider.create().create(Api.class);
        api.query(id_token, strSearch, page, 10, "desc", "create_time")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PersonJson personJson) {
                        searchList.clear();
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
                            searchList.add(new Person(userid, avatar, name, gender, introduction, location, follow, follower, myfollow));
                        }
                        adapter.addAll(searchList);
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
    public void onLoadMore() {
        page = page + 1;
        initRefresh(page);
    }

    @Override
    public void onRefresh() {

    }
}
